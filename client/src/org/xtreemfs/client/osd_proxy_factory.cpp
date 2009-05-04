// Copyright 2009 Minor Gordon.
// This source comes from the XtreemFS project. It is licensed under the GPLv2 (see COPYING for terms and conditions).

#include "org/xtreemfs/client/osd_proxy_factory.h"
#include "org/xtreemfs/client/dir_proxy.h"
using namespace org::xtreemfs::client;


OSDProxyFactory::OSDProxyFactory( YIELD::auto_Object<DIRProxy> dir_proxy, YIELD::StageGroup& osd_proxy_stage_group )
  : dir_proxy( dir_proxy ), osd_proxy_stage_group( osd_proxy_stage_group )
{ }

OSDProxyFactory::~OSDProxyFactory()
{
  osd_proxy_cache_lock.acquire();
  for ( YIELD::STLHashMap<OSDProxy*>::iterator osd_proxy_i = osd_proxy_cache.begin(); osd_proxy_i != osd_proxy_cache.end(); osd_proxy_i++ )
    YIELD::Object::decRef( *osd_proxy_i->second );
  osd_proxy_cache.clear();
  osd_proxy_cache_lock.release();
}

YIELD::auto_Object<OSDProxy> OSDProxyFactory::createOSDProxy( const std::string& uuid )
{
  return createOSDProxy( dir_proxy->getURIFromUUID( uuid ) );
}

YIELD::auto_Object<OSDProxy> OSDProxyFactory::createOSDProxy( const YIELD::URI& uri )
{
  uint32_t uri_hash = YIELD::string_hash( uri.get_host() ) ^ uri.get_port();

  osd_proxy_cache_lock.acquire();
  OSDProxy* osd_proxy = osd_proxy_cache.find( uri_hash );
  osd_proxy_cache_lock.release();

  if ( osd_proxy == NULL )
  {
    osd_proxy = OSDProxy::create( osd_proxy_stage_group, uri, dir_proxy->get_ssl_context(), dir_proxy->get_log() ).release();
    osd_proxy->set_operation_timeout_ns( dir_proxy->get_operation_timeout_ns() );
    osd_proxy->set_reconnect_tries_max( dir_proxy->get_reconnect_tries_max() );
    osd_proxy_cache_lock.acquire();
    osd_proxy_cache.insert( uri_hash, osd_proxy );
    osd_proxy_cache_lock.release();
  }

  return osd_proxy->incRef();
}

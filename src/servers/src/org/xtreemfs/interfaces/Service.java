package org.xtreemfs.interfaces;

import org.xtreemfs.*;
import org.xtreemfs.common.buffer.ReusableBuffer;
import org.xtreemfs.interfaces.utils.*;
import yidl.Marshaller;
import yidl.Struct;
import yidl.Unmarshaller;




public class Service extends Struct
{
    public static final int TAG = 2009082652;
    
    public Service() { type = ServiceType.SERVICE_TYPE_MIXED; data = new ServiceDataMap();  }
    public Service( ServiceType type, String uuid, long version, String name, long last_updated_s, ServiceDataMap data ) { this.type = type; this.uuid = uuid; this.version = version; this.name = name; this.last_updated_s = last_updated_s; this.data = data; }

    public ServiceType getType() { return type; }
    public void setType( ServiceType type ) { this.type = type; }
    public String getUuid() { return uuid; }
    public void setUuid( String uuid ) { this.uuid = uuid; }
    public long getVersion() { return version; }
    public void setVersion( long version ) { this.version = version; }
    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }
    public long getLast_updated_s() { return last_updated_s; }
    public void setLast_updated_s( long last_updated_s ) { this.last_updated_s = last_updated_s; }
    public ServiceDataMap getData() { return data; }
    public void setData( ServiceDataMap data ) { this.data = data; }

    // java.io.Serializable
    public static final long serialVersionUID = 2009082652;    

    // yidl.Object
    public int getTag() { return 2009082652; }
    public String getTypeName() { return "org::xtreemfs::interfaces::Service"; }
    
    public int getXDRSize()
    {
        int my_size = 0;
        my_size += 4;
        my_size += uuid != null ? ( ( uuid.getBytes().length + Integer.SIZE/8 ) % 4 == 0 ) ? ( uuid.getBytes().length + Integer.SIZE/8 ) : ( uuid.getBytes().length + Integer.SIZE/8 + 4 - ( uuid.getBytes().length + Integer.SIZE/8 ) % 4 ) : 0;
        my_size += ( Long.SIZE / 8 );
        my_size += name != null ? ( ( name.getBytes().length + Integer.SIZE/8 ) % 4 == 0 ) ? ( name.getBytes().length + Integer.SIZE/8 ) : ( name.getBytes().length + Integer.SIZE/8 + 4 - ( name.getBytes().length + Integer.SIZE/8 ) % 4 ) : 0;
        my_size += ( Long.SIZE / 8 );
        my_size += data.getXDRSize();
        return my_size;
    }    
    
    public void marshal( Marshaller marshaller )
    {
        marshaller.writeInt32( type, type.intValue() );
        marshaller.writeString( "uuid", uuid );
        marshaller.writeUint64( "version", version );
        marshaller.writeString( "name", name );
        marshaller.writeUint64( "last_updated_s", last_updated_s );
        marshaller.writeMap( "data", data );
    }
    
    public void unmarshal( Unmarshaller unmarshaller ) 
    {
        type = ServiceType.parseInt( unmarshaller.readInt32( "type" ) );
        uuid = unmarshaller.readString( "uuid" );
        version = unmarshaller.readUint64( "version" );
        name = unmarshaller.readString( "name" );
        last_updated_s = unmarshaller.readUint64( "last_updated_s" );
        data = new ServiceDataMap(); unmarshaller.readMap( "data", data );    
    }
        
    

    private ServiceType type;
    private String uuid;
    private long version;
    private String name;
    private long last_updated_s;
    private ServiceDataMap data;    

}


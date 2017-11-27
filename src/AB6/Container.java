package AB6;


public class Container {

	private Object _obj;
	
	private int _key;
	
	public Container(Object obj, int key)
	{
		_key = key;
		_obj = obj;
	}
	
	public int getKey()
	{
		return _key;
	}
	
	public Object getObject()
	{
		return _obj;
	}

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Container))return false;
	    Container otherContainer = (Container)other;
	    return otherContainer._key == this._key;
	}
}

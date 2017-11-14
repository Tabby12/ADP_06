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
}

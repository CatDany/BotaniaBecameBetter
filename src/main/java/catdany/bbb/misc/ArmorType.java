package catdany.bbb.misc;

public enum ArmorType
{
	HELM(0, "Helm"),
	CHEST(1, "Chest"),
	LEGS(2, "Legs"),
	BOOTS(3, "Boots");
	
	private int typeIndex;
	private String typeName;

	private ArmorType(int typeIndex, String typeName)
	{
		this.typeIndex = typeIndex;
		this.typeName = typeName;
	}
	
	public int getTypeIndex()
	{
		return typeIndex;
	}
	
	public String getTypeName()
	{
		return typeName;
	}
}

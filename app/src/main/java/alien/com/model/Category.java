package alien.com.model;

public class Category
{

    public String Id;
    public String categoryName;

    public Category(String categoryName,String Id)
    {
        this.Id = Id;
        this.categoryName = categoryName;
    }

    public String getId() {
        return Id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

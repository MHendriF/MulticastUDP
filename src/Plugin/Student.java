package Plugin;

import java.io.Serializable;

public class Student implements Serializable {
    public Student(int id, String name, String address1) {
    this.id = id;
    this.name = name;
    this.addressLine = address1;
    }

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String addressLine;

    public int getId() {
    return id;
    }

    public void setId(int id) {
    this.id = id;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getAddressLine() {
    return addressLine;
    }

    public void setAddressLine(String addressLine) {
    this.addressLine = addressLine;
    }

    public String toString() {
    return "Id = " + getId() + " Name = " + getName() + " Address = " + getAddressLine();
    }
}

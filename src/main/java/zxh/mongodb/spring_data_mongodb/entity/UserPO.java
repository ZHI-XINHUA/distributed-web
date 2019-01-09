package zxh.mongodb.spring_data_mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * user 实体类-->MongoDB实体类映射
 */
@Document(collection = "coll_user")
public class UserPO implements Serializable {
    private static final long serialVersionUID = -2450713238985657015L;

    @Id
    private String id;

    @Field("namecn")
    private String name;

    @Field
    private int sex;

    @Indexed(name = "age_index",direction = IndexDirection.DESCENDING)
    @Field
    private int age;

    //索引
    @Indexed(name = "age_index",direction = IndexDirection.ASCENDING)
    @Field
    private Timestamp birth;


    @Field
    private String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

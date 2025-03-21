package org.ecnu.backend.entity;

public class Surveyor {
    private Long id; // 主键ID
    private String name; // 问卷调查者姓名
    private String email; // 问卷调查者邮箱
    private String phone; // 问卷调查者电话
    private String organization; // 所属组织或机构

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Surveyor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}
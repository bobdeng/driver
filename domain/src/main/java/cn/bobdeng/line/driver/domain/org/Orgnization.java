package cn.bobdeng.line.driver.domain.org;

import lombok.Data;

@Data
public class Orgnization {
    private int id;
    private String name;
    private String location;
    private int maxDistance;
}

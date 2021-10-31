package fun.acgm.myboot.entity.senator;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("us_senator")
public class Senator {
    @Id
    private Long id;

    private String name;

    private String state;

    private String party;

    private String cls;

    private String office;

    private String phone;

    private String link;

    private Long createTime;

    private Long updateTime;
}

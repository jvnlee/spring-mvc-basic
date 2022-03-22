package spring.springmvcbasic.basic;

import lombok.Data;

@Data
public class SampleData {

    // @Data는 @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 자동으로 적용해줌

    private String username;
    private int age;

}

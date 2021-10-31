package fun.acgm.myboot.repository.senator;

import fun.acgm.myboot.entity.senator.Senator;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SenatorRepository extends ReactiveCrudRepository<Senator, Long> {

    @Query("select name, create_time as createTime from us_senator where name = :name")
    Flux<Senator> searchAll(String name);


}

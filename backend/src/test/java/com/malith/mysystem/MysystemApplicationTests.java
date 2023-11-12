package com.malith.mysystem;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class MysystemApplicationTests extends AbstractTestContainerUnitTest{

    @Test
    void canStartMysqlDb(){
        Assertions.assertThat(mySQLContainer.isRunning()).isTrue();
        Assertions.assertThat(mySQLContainer.isCreated()).isTrue();
        System.out.println();
    }
}

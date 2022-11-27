import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainClientTest {
    MainClient1 sut;

    @BeforeAll
    public static void initSuite() {
        System.out.println("Запуск всех тестов");
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("Завершение тестирования кода");
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Запуск теста");
        sut = new MainClient1();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Завершение теста");
        sut = null;
    }

    @Test
    public void portReadTest(){
        String port = "8089";

        String result = sut.portRead();

        assertEquals(port, result);
    }
}

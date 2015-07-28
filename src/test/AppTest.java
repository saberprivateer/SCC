import com.company.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Arrays;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    /*
    @Test
    public void testMain() throws IOException {
        String[] args = new String[]{"go"};
        Main.main(args);
        Assert.assertTrue(true);
    }
    */

    @Test
    public void testData() throws IOException {
        Main.data("testcase1");
        Assert.assertTrue(true);
    }

    @Test
    public void testTestcase() throws URISyntaxException, IOException {
        Main tc1 = new Main();
        int[] answer1 = new int[5];
        Main.testcase("testcase1", answer1);
        Assert.assertArrayEquals("Test Case 1 " + Arrays.toString(answer1), new int[]{3, 3, 3, 0, 0}, answer1);
    }

    @Test
    public void testTestcase2() throws URISyntaxException, IOException {
        Main tc2 = new Main();
        int[] answer1 = new int[5];
        Main.testcase("testcase2", answer1);
        Assert.assertArrayEquals("Test Case 2 "+ Arrays.toString(answer1), new int[]{3, 3, 2, 0, 0}, answer1);
    }

    @Test
    public void testTestcase3() throws URISyntaxException, IOException {
        Main tc3 = new Main();
        int[] answer1 = new int[5];
        Main.testcase("testcase3", answer1);
        Assert.assertArrayEquals("Test Case 3 "+ Arrays.toString(answer1), new int[]{3, 3, 1, 1, 0}, answer1);
    }

    @Test
    public void testTestcase4() throws URISyntaxException, IOException {
        Main tc4 = new Main();
        int[] answer1 = new int[5];
        Main.testcase("testcase4", answer1);
        Assert.assertArrayEquals("Test Case 4 "+ Arrays.toString(answer1), new int[]{7, 1, 0, 0, 0}, answer1);
    }
    @Test
    public void testTestcase5() throws URISyntaxException, IOException {
        Main tc5 = new Main();
        int[] answer1 = new int[5];
        Main.testcase("testcase5", answer1);
        Assert.assertArrayEquals("Test Case 5 "+ Arrays.toString(answer1), new int[]{6, 3, 2, 1, 0}, answer1);
    }

    @Test
    public void testLog() {
        Main.log(9);
        Assert.assertEquals("Ints work", Integer.toString(9), outContent.toString().trim());
    }

    @Test
    public void testLogtext() {
        Main.log("hello");
        Assert.assertEquals("Text with quotes works", "hello", outContent.toString().trim());
    }

}
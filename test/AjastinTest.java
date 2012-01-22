import grafiikat.Ajastin;
import javax.swing.JPanel;
import org.junit.*;
import static org.junit.Assert.*;


public class AjastinTest {
    
    private JPanel ptesti;
    private Ajastin ajastin;
    
    public AjastinTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        ptesti = new JPanel();
        ajastin = new Ajastin(ptesti);
    }
    
    @After
    public void tearDown() {
    }
    

       
    
    
    
}

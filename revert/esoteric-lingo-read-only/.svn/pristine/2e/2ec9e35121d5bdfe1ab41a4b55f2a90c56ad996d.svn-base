/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ook;
import java.io.Console;

/**
 *
 * @author nmondal
 */
public class Main {

    
    static void Encode(Encoder encoder)
    {
        String text = "";
        String line = "";
        Console console = System.console(); 
        System.out.println("[press EOF to exit, which in UNIX is CTRL-D and in WINDOWS CTRL-Z]");
        
        while ( true )
        {
            
            try
            {
                line = console.readLine();
                
                if ( line == null )
                {
                    break;
                }
                line = line.trim();
                
                if ( !line.isEmpty() )
                {
                    text+= line;
                }
            }
            catch(Exception e)
            {
                break;
            }
        }
        encoder.encode(text);
    }
    
    static void Decode(Interpreter interpreter)
    {
        String program = "";
        String line = "";
        Console console = System.console();
        System.out.printf("Tape Length : %d BYTES\n", Interpreter.DEFAULT_TAPE_LENGTH );
        System.out.println("[press EOF to exit, which in UNIX is CTRL-D and in WINDOWS CTRL-Z]");
        
        while ( true )
        {
            
            try
            {
                line = console.readLine();
                
                if ( line == null )
                {
                    break;
                }
                line = line.trim();
                
                if ( !line.isEmpty() )
                {
                    program+= line;
                }
            }
            catch(Exception e)
            {
                break;
            }
        }
        interpreter.interpreteAndRun(program);
        //interpreter.interpreteAndRun(helloWorld_OOK);
        System.out.printf("\n{It is Highly probable that you are without any life.\n" );
        System.out.printf("--Noga.}\n" );
    }
    
    
    static final String helloWorld_BF="++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
    static final String helloWorld_OOK= Interpreter.compileFromBF_2_OOK(helloWorld_BF);
       
    
    static void usage()
    {
        System.out.printf("Greetings, Let me present you this program:- \n" );
        System.out.printf("Usage : java -jar ook.jar [lingo] [-e] [-s:<size>].\n");
        System.out.printf("lingo : -bf  --> BrainFuck! \n");
        System.out.printf("lingo : -ook --> Ook! \n");
        System.out.printf("With -s:<size> in the end : SIZE of the TAPE, default is : 1024 BYTES.\n");
        System.out.printf("With -e in the end, ENCODES Homo Sapiens ENGLISH INTO Alianize[BrainFuck] or Pongo[Ook!].\n" );
        System.out.printf("If you are running it now, it is highly probable that you are without any life.\n" );
        System.out.printf("--Noga.\n" );
        System.exit(23);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Interpreter interpreter = new Interpreter(Interpreter.InterpreterLanguage.OOK);
        Encoder encoder = new Encoder(Interpreter.InterpreterLanguage.OOK );
        
        if ( args.length > 0 && args[args.length-1].toLowerCase().startsWith("-s:") )
        {
            String[] arr = args[args.length-1].split(":");
            Interpreter.DEFAULT_TAPE_LENGTH = Integer.parseInt(arr[1]);
        }
        if ( args.length == 2 )
        {
            if ( args[1].equalsIgnoreCase("-e") )
            {
                //Instead of decode, encode.
                if ( args[0].equalsIgnoreCase("-ook")) 
                {

                }
                else if ( args[0].equalsIgnoreCase("-bf") )
                {
                    encoder = new Encoder(Interpreter.InterpreterLanguage.BRAINFUCK );

                }
                Encode(encoder);
            }
            
        }
        else if ( args.length == 1 )
        {
            if ( args[0].equalsIgnoreCase("-ook")) 
            {

            }
            else if ( args[0].equalsIgnoreCase("-bf") )
            {
                interpreter = new Interpreter(Interpreter.InterpreterLanguage.BRAINFUCK);
            }
            Decode(interpreter);
        }
        else
        {
            usage();
        }
        
    }
}

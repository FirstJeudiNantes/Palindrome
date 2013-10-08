/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ook;

import java.util.HashMap;
import java.util.ArrayList ;
import java.io.Console;


/**
 *
 * @author nmondal
 */
public class Interpreter {
    public enum Command
    {
        MOVE_MEM_PTR_TO_NEXT_CELL,
        MOVE_MEM_PTR_TO_PREV_CELL,
        CELL_VALUE_INCREMENT,
        CELL_VALUE_DECREMENT,
        CELL_VALUE_OUTPUT_CHAR_ASCII,
        CELL_VALUE_READ_BYTE,
        WHILE_CELL_VALUE_NON_ZERO_START,
        WHILE_END
        
    }
    public enum InterpreterLanguage
    {
        OOK,
        BRAINFUCK
    }
    public static HashMap<Character,Command> brainFuckMap;
    public static HashMap<String,Character> ookMap;
    public static HashMap<Character,String> bf_2_ook_Map;
    
    public static int DEFAULT_TAPE_LENGTH = 1024 ;
    
    static
    {
        //static construction of the maps.
        brainFuckMap = new HashMap<Character,Command>();
        ookMap = new HashMap<String,Character>();
        bf_2_ook_Map = new HashMap<Character,String>();
        /********BF  ******/
        brainFuckMap.put('>', Command.MOVE_MEM_PTR_TO_NEXT_CELL);
        brainFuckMap.put('<', Command.MOVE_MEM_PTR_TO_PREV_CELL);
        brainFuckMap.put('+', Command.CELL_VALUE_INCREMENT);
        brainFuckMap.put('-', Command.CELL_VALUE_DECREMENT);
        brainFuckMap.put('.', Command.CELL_VALUE_OUTPUT_CHAR_ASCII);
        brainFuckMap.put(',', Command.CELL_VALUE_READ_BYTE);
        brainFuckMap.put('[', Command.WHILE_CELL_VALUE_NON_ZERO_START);
        brainFuckMap.put(']', Command.WHILE_END);
        
        /******** Ook ******/
        ookMap.put("ook.ook?", '>');
        ookMap.put("ook?ook.", '<');
        ookMap.put("ook.ook.", '+');
        ookMap.put("ook!ook!", '-');
        ookMap.put("ook!ook.", '.');
        ookMap.put("ook.ook!", ',');
        ookMap.put("ook!ook?", '[');
        ookMap.put("ook?ook!", ']');
        /***********BF to OOK ***********/
        
        bf_2_ook_Map.put( '>', "ook.ook?");
        bf_2_ook_Map.put( '<', "ook?ook.");
        bf_2_ook_Map.put( '+', "ook.ook.");
        bf_2_ook_Map.put( '-', "ook!ook!");
        bf_2_ook_Map.put( '.', "ook!ook.");
        bf_2_ook_Map.put( ',', "ook.ook!");
        bf_2_ook_Map.put( '[', "ook!ook?");
        bf_2_ook_Map.put( ']', "ook?ook!");
        
    }
    private InterpreterLanguage language;
    private byte[] tape ;
    private ArrayList<Command> program;
    
    public static boolean isBrainFuckChar(char c)
    {
        if ( c == '>' || c == '<' || c == '+' 
                        || c == '-' || c == '.'
                        || c == ',' || c == '[' || c == ']')
        {
            return true ;
        }
        return false;
    }
    
    public static boolean isOokChar(char c)
    {
        if ( c == 'o' || c == 'k' || c == '.' 
                        || c == '!' || c == '?' )
        {
            return true;
        }
        return false;
    }
    
    public static String getCompactedOokCode(String lines)
    {
        lines = lines.toLowerCase();
        String newProgram = "" ;
        for ( int i = 0 ; i < lines.length();i++)
        {
            char c = lines.charAt(i);
            if ( isOokChar(c) )
            {
                newProgram += c ;
            }
        }
        return newProgram;
    }
    public static String getCompactedBrainFuckCode(String lines)
    {
        lines = lines.toLowerCase();
        String newProgram = "" ;
        for ( int i = 0 ; i < lines.length();i++)
        {
            char c = lines.charAt(i);
            if ( isBrainFuckChar(c))
            {
                newProgram += c ;
            }
        }
        return newProgram;
    }
    public static String compileFromBF_2_OOK(String bfCode)
    {
        String newProgram = getCompactedBrainFuckCode(bfCode);
        String tmpProgram = "";
        for ( int i = 0 ; i < newProgram.length();i++)
        {
            char c = newProgram.charAt(i);
            if (!bf_2_ook_Map.containsKey(c ) )
            {
                System.err.printf("Not a recognizable command '%c' \n" , c);
                return null;
            }
            tmpProgram += bf_2_ook_Map.get(c ) + " ";
            
        }
        
        return tmpProgram;

    }
    
    public static String compileFromOOK_2_BF(String ookCode)
    {
        String newProgram = getCompactedOokCode(ookCode);
        String tmpProgram = "";
        for ( int i = 0 ; i < newProgram.length();i+=8)
        {
            String line = newProgram.substring(i, i+8);
            if ( !line.isEmpty() )
            {
                if ( ! ookMap.containsKey(line) )
                {
                    System.err.printf("Not a recognizable command '%s' \n" , line);
                    return null;
                }
                tmpProgram += ookMap.get(line);
            }
        }
        
        return tmpProgram;
    }
    
    
    private boolean compile(String lines)
    {
        lines = lines.toLowerCase();
        
        String newProgram = "" ;
        if ( InterpreterLanguage.OOK == language ) 
        {
            newProgram = getCompactedOokCode(lines);
            //Now we have the real code.
            //read 8 bytes data, to generate the abstract command code.
            //Convert to BrainFuck.
            
                
            newProgram = compileFromOOK_2_BF(newProgram);
            
            if ( newProgram == null )
                return false;
            
        }
        else if (InterpreterLanguage.BRAINFUCK == language ) 
        {
            newProgram = getCompactedBrainFuckCode(lines);
            //Now we have the real code.
        }
        //Now we have a brainfuck code all the time.
        //Compile it proper....
        try
        {
            program = new ArrayList<Command>() ;
            for ( int i = 0 ; i < newProgram.length(); i++ )
            {
                program.add(  brainFuckMap.get(  newProgram.charAt(i) ));
            }
            tape = new byte[DEFAULT_TAPE_LENGTH];
            for ( int i = 0 ; i <DEFAULT_TAPE_LENGTH;i++ )
            {
                tape[i]=0;
            }
        }
        catch(Exception e)
        {
            System.err.printf("Error compiling!");
            e.printStackTrace();
            return false ;
        }
        System.out.printf("{{%s}}\n",newProgram);
        return true;
    }
    
    public void interpreteAndRun(String lines)
    {
        interpreteAndRun(lines,language);
    }
    public void interpreteAndRun(String lines,InterpreterLanguage lingo)
    {
        language = lingo ;
        Console console = System.console();
        if  ( compile ( lines ))
        {
            //Run the code.
            int PROGRAM_COUNTER = 0;
            int TAPE_PTR = 0 ;
            
            while ( PROGRAM_COUNTER >=0 && PROGRAM_COUNTER < program.size() )
            {
                switch ( program.get(PROGRAM_COUNTER)  )
                {
                    case MOVE_MEM_PTR_TO_NEXT_CELL :
                        TAPE_PTR++;
                        break;
                    case  MOVE_MEM_PTR_TO_PREV_CELL :
                        TAPE_PTR--;
                        break;
                    case CELL_VALUE_INCREMENT :
                        tape[TAPE_PTR]++;
                        break;
                    case CELL_VALUE_DECREMENT :
                        tape[TAPE_PTR]--;
                        break;
                    case CELL_VALUE_OUTPUT_CHAR_ASCII:
                        byte b = tape[TAPE_PTR];
                        char c = (char)b;
                        System.out.print(c);
                        System.out.flush();
                        break;
                    case CELL_VALUE_READ_BYTE:
                        try
                        {
                            int read = console.reader().read();
                            tape[TAPE_PTR] = (byte)read;
                        }
                        catch (Exception e)
                        {
                            
                        }
                        break;
                    case WHILE_CELL_VALUE_NON_ZERO_START:
                        if ( tape[TAPE_PTR] == 0 )
                        {
                            while (  PROGRAM_COUNTER < program.size() )
                            {
                                Command com = program.get(PROGRAM_COUNTER);
                                if ( Command.WHILE_END == com )
                                {
                                    break;
                                }
                                PROGRAM_COUNTER++;
                            }
                        }
                        break;
                    case WHILE_END:
                        if ( tape[TAPE_PTR] != 0 )
                        {
                            while (  PROGRAM_COUNTER >= 0 )
                            {
                                Command com = program.get(PROGRAM_COUNTER);
                                if ( Command.WHILE_CELL_VALUE_NON_ZERO_START == com )
                                {
                                    PROGRAM_COUNTER--;
                                    break;
                                }
                                PROGRAM_COUNTER--;
                            }
                        }
                        break;
                }
                PROGRAM_COUNTER++;
            }
        }
    }
    
    public Interpreter()
    {
        language =  InterpreterLanguage.OOK ;
    }
    public Interpreter(InterpreterLanguage lingo)
    {
        language =  lingo ;
    }
    
    
}

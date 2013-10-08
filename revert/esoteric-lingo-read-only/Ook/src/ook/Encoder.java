/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ook;

/**
 *
 * @author nmondal
 */
public class Encoder {
    
    Interpreter.InterpreterLanguage language ;
    
    public static String startEncoding = "+++++ +++++ [ >"  ;           
    
    public static String encode2BF(String ordinaryString)
    {
        
        String s="";
        //Steps are:-
        //Gather the length of the string.
        int length  = ordinaryString.length() ;
        //use the first location to store the length.
        //hence increment the location exactly that much.
        
        double avg = 0.0;
        
        for ( int i = 0 ; i < length ; i++ )
        {
           byte b = (byte)ordinaryString.charAt(i);
           avg += b;
        }
        avg = avg/length ;
        
        int count = (int)avg/10  ; 
        
        s= startEncoding ;
        
        for ( int i = 0 ; i < count; i++ )
        {
            s+= "+" ;
        }
        s+= " <- ] >" ;
        
        byte val = (byte)(count*10);
        
        byte val_now = val ;
        
        for ( int i = 0 ; i < length ; i++ )
        {
           byte b = (byte)ordinaryString.charAt(i);
           if ( b > val_now )
           {
               byte b_diff = (byte)(b - val_now) ;
               for ( byte j = 0 ; j < b_diff; j++ ) 
               {
                   s+="+";
               }
           }
           else if ( b < val_now )
           {
               byte b_diff = (byte)( val_now - b) ;
               val_now = b_diff ;
               for ( byte j = 0 ; j < b_diff; j++ ) 
               {
                   s+="-";
               }
           }
           val_now = b ;
           s+=".";
           
        }
        return s;
        
        
    }
    public static String encode2OOK(String ordinaryString)
    {
        String bf = encode2BF(ordinaryString);
        return Interpreter.compileFromBF_2_OOK(bf);
    }
    public Encoder()
    {
        language = Interpreter.InterpreterLanguage.OOK ;
    }
    public Encoder(Interpreter.InterpreterLanguage lingo )
    {
        language = lingo ;
    }
    public void encode(String string)
    {
        if ( Interpreter.InterpreterLanguage.OOK == language  )
        {
            System.out.println(encode2OOK(string));
        }
        else
        {
            System.out.println( encode2BF(string));
        }
    }
}

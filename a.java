public class a {
    public static void main(String[] args){
        String s ="ciao";
        int start = s.length();
        //int vowelsCount = (start - s.replaceAll("[aeiou]", s).length());
        
        System.out.println(s.replaceAll("([^aeiou])", s));
    }
    
}

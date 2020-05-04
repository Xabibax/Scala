package rational;

class RationalJavaTest {
    public static final Rational r1 = new Rational(1).add(new Rational(1, 2));

    public static void main(String[] args){
        System.out.println(r1);
        System.out.println(r1.add(r1));
    }
}
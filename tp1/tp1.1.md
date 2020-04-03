
De Java à Scala avec des objets fonctionnels
Table des matières

    1. Objectif
    2. Un point de départ en Java
    3. De Java à Scala
    4. Passons à Scala
        4.1. Mutabilité (val et var)
        4.2. Accesseurs
        4.3. Syntaxe étendue des identificateurs et notation infixe
        4.4. Classe et objet compagnon
        4.5. Le pattern apply

1 Objectif

Ce TP est un support à la compréhension du cours Objets fonctionnels et prolonge la prise en main d'IntelliJ.
2 Un point de départ en Java

On considère la classe suivante, Rational, qui implémente, de manière naïve, des nombres rationnels, à recopier, en tant que classe Java, dans un paquetage rational d'un projet Scala Rational. On peut effectivement mélanger dans un projet Scala du code Java et du code Scala !

public class Rational {
    private int n;
    private int d;

    public Rational(int n, int d) {
        this.n = n;
        this.d = d;
    }
    public Rational(int n){
        this(n, 1);
    }
    public int getN() {
        return n;
    }
    public void setN(int i) { n = i; }
    public int getD() {
        return d;
    }
    public void setD(int i) { d = i; }

    @Override
    public String toString() {
        return n + "/" + d;
    }

    public Rational add(Rational that){
        return new Rational(
                n * that.getD() + that.getN() * d,
                d * that.getD());
    }
}

Pourquoi naïve ? Parce qu'un nombre rationnel est par nature immutable, sa valeur ne change pas au cours du temps. Dans la terminologie de la programmation par objets, l'état d'une instance de la classe Rational est défini une fois pour toute lors de son instanciation. Ce n'est pas le cas ci-dessus, par exemple, le programme de test suivant modifie (dangeureusement) le dénominateur de r1.

public class Test {
    public static Rational r1 = new Rational(1).add(new Rational(1, 2));

    public static void main(String[] args){
        System.out.println(r1);
        r1.setD(0);
        System.out.println(r1);
    }
}

Comment empêcher ça efficacement, sans lever d'exception à l'exécution ?

    Utiliser le mot-clé final ?
    Modifier les accesseurs des numérateurs et dénominateurs d'un rationnel ?

java.png Pour mémoire, les variables sont par défaut mutables et peuvent être déclarées immutables à l'aide du mot-clé final.

Modifiez la classe Rational de sorte qu'il ne soit plus possible de modifier l'état de r1 à partir de la classe Test.
3 De Java à Scala

On a vu précédemment qu'il était possible de mélanger du code Java et Scala dans le même projet. C'est qu'il est en fait possible d'interopérer entre les deux langages. Il est aussi souvent assez direct de traduire un programme écrit en Java en un programme écrit en Scala, ce que permet de faire dans une certaine limite l'IDE à l'aide de la commande Refactor.

    Sélectionnez la classe Test et choisissez dans le menu contextuel, la commande Refactor > Convert to Scala. Vous avez maintenant deux versions de Test, une classe Java et un objet Scala (on parle aussi de classe singleton : cet objet correspond à une classe qui n'a qu'une instance). À ce stade, il n'est pas possible d'exécuter le test parce que ces deux définitions entrent en collision. Mais vous pouvez effacer la version Java et exécuter le test à partir de l'objet Scala.
    Convertissez en Scala la classe Rational afin d'obtenir un programme tout en Scala (après suppression de la classe Java).

Alternativement, il serait aussi possible de repartir de la classe Test en Java et d'utiliser la classe Rational en Scala.

Notez qu'on aimerait pouvoir effectuer la transformation inverse de l'objet Scala en une classe Java mais l'IDE ne le permet pas (malgré l'existence d'une commande Refactor > Convert to Java en grisé).

De manière générale, l'interopérabilité entre Scala et Java est due au fait que les deux langages sont compilés en pseudocode Java, exécutable par la machine virtuelle Java (JVM), avec la possibilité de transformer simplement tout programme Java en un programme Scala (à quelques détails près), l'inverse n'étant pas vrai (certaines constructions de Scala n'ont pas de traduction directe en Java - subtilement, tout programme Java se compile en pseudocode, mais tout pseudocode n'a pas d'équivalent direct en Java).

On va continuer avec le programme complètement écrit en Scala.

attention.png En jouant avec des constructions de même nom en Scala et Java, il n'est pas exclu que vous vous retrouviez avec du pseudo-code incohérent. En cas de doute, n'hésitez-pas à reconstruire le projet (menu Build).
4 Passons à Scala
4.1 Mutabilité (val et var)

Revenons à la version Scala de la classe Rational. La première chose à faire est de vérifier qu'il n'est effectivement pas possible de modifier un rationnel. Par exemple, en utilisant REPL :

scala> import rational._
import rational._

scala> Test.r1.d = 0

tip.png Dans la directive d'importation, _ est le caractère joker (* en Java) qui rend visible toutes les déclarations disponibles au premier niveau dans le paquetage rational.

attention.png Suivant les modifications initialement effectuées sur le programme Java, la variable r1 peut avoir été déclarée mutable (mot-clé var) ou immutable (val). Notez bien qu'il n'y a pas de lien entre le fait que la variable soit ou pas mutable et le fait que l'objet référencé par la variable le soit ou pas.

Faites en sorte que les instances de la classe Rational soit immutables mais que r1 soit mutable.

```scala
import rational._
// import rational._

Test.r1 = new Rational(0)
// Test.r1: rational.Rational = 0/1
```

tip.png Les modification du programme sont sans influence sur REPL/la console Scala existante. Si vous voulez voir les effets de la modification, il faut démarrer une nouvelle console (et fermer la première si elle n'est plus utile).

## 4.2 Accesseurs

En Scala, contrairement à Java, l'utilisation d'accesseurs est inutile (nous reviendrons sur ce point ultérieurement). Supprimez en conséquence les accesseurs restants.

````scala
import rational._
// import rational._

Test.r1.n
// res0: Int = 2
````

Qu'est-il encore possible de simplifier ?

Que se passe-t-il si on efface les mots-clés val associés aux paramètres de la classe Rational ?
##4.3 Syntaxe étendue des identificateurs et notation infixe

Scala élargit l'ensemble des caractères ASCII utilisables pour définir des identificateurs aux caractères !#$%&*+-/:<=>?@^|~. Il est donc possible de renommer l'addition +.

De plus, il est possible pour les méthodes ne comportant qu'un argument d'indifféremment utiliser l'habituelle notation pointée ou une notation infixe qui permet de se passer du point et des parenthèses. Pour les entiers habituels, l'écriture 1 + 2 est par exemple un équivalent plus naturel de 1.+(2) (+ est bien une méthode qui s'applique à des objets).

- Modifiez le programme en conséquence.
- Ajoutez la multiplication.

## 4.4 Classe et objet compagnon

En préliminaire, il est utile de bien comprendre la différence entre une classe et un objet. Le corps d'une classe et d'un objet sont de même nature : ils contiennent la définition de champs d'instances de la classe dans le premier cas et de l'objet lui-même dans le second. Si on parle de classe singleton, c'est qu'il y a une seule instance de cette classe et on peut directement associer un nom à cet objet, ce que fait la structure Scala object. Comment vérifier concrètement que Test est bien un objet ? En demandant son évaluation à REPL (ou en utilisant une feuille de travail). Quel est son type ?

Ainsi, quand on exécute Test.main(Array()), il s'agit bien d'un appel de méthode standard dont l'objet Test est le receveur (contrairement à Java).

Un pattern standard en Scala est d'associer une classe à un objet de même nom, qu'on appelle son objet compagnon. Il faut alors que classe et objet soient définis dans le même fichier. Pensez à la situation en Java où une classe contient à la fois des champs statiques (ou de classe) et des champs dynamiques (ou d'instance). En Scala, les premiers (les champs statiques) vont dans l'objet compagnon.

Transformez Test en objet compagnon de la classe Rational.

## 4.5 Le pattern apply

En Scala, toute expression de la forme e ( args ), où e est aussi une expression et args une séquence d'arguments, est en fait un raccourci syntaxique pour e .apply( args ).

Ce pattern, associé à celui de l'objet compagnon, peut être utilisé pour créer des méthodes usines à objets (factories).

En effet, si on définit dans l'objet compagnon d'une classe quelconque Class une méthode apply prenant en paramètre les mêmes paramètres que le constructeur primaire et créant à partir de ces paramètres une instance de Class, il est alors possible de créer une instance de Class à l'aide d'un pseudo appel à Class. De même pour les constructeurs secondaires.

Concrètement, si on définit dans l'objet Rational la méthode apply ainsi :

`
def apply(n: Int, d: Int) = new Rational(n, d)   
`

On peut alors indifférement créer un rationnel 1/2 en utilisant l'expression new Rational(1, 2) ou Rational(1, 2). Dans le premier cas, Rational est une classe et c'est son constructeur primaire qui est directement appelé, dans le second, c'est un objet et c'est la méthode apply de l'objet qui est appelée.

Mettez en place ce pattern pour les deux constructeurs de Rational et modifiez la méthode main afin de l'utiliser.

~~~~
Auteur: Jacques Noyé

Email: jacques.noye@imt-atlantique.fr

Created: 2020-04-03 Fri 13:23

Validate

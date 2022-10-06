import java.io.File
import java.util.*

/**
 * Lambda expressions or simply lambdas
 *
 * Lambdas and collections
 */

/**
 * Capturing variables from the context
 */

fun printMessageWithPrefix (messages: Collection<String>, prefix: String){
    messages.forEach {
        println("$prefix $it")
    }
}

fun printProblemCounts (responses: Collection<String>){
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")){
            clientErrors++
        } else if (it.startsWith("5")){
            serverErrors++
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

fun countClickingOnButton(button: Button): Int{
    button.onClick()
    return button.pressCounting
}

/**
 * Member references
 *
 * can be called by :: operator
 */

fun salute() = println ("Salute!")

fun Person.isAdult() = age >= 21

/**
 * filter and map
 */

/**
 * applying a predicate to a collection
 */

/**
 * flatMap and flatten
 */

/**
 * Lazy collection operations: sequences
 *
 * Sequence interface
 * provide only one method - iterator
 *
 * asSequence() extension function
 *
 * using asSequence() there is no intermediate collections will be created
 * this is give noticeable positive impact on performance
 *
 */

/**
 * Intermediate and terminal operations
 *
 * an intermediate operation returns another sequence,
 * which knows how to transform the elements of the original sequence
 * intermediate operators are always lazy
 *
 * a terminal operation returns a result, which may be a collection,
 * an element, a number, or any other object
 *
 * in sequences all operations are applied to each element sequentially -
 * the first element is processed (in this example - mapped, filtered) then the second and so on
 *
 */

/**
 * Generated sequence
 */



fun main(){

    val people = listOf<Person>(Person("Alice", 25),
        Person("Bob", 31),
        Person ("Mark", 20),
        Person("Carol", 31),
        Person("Armen", 55),
        Person("Dan", 21))

    findTheOldest(people)

    println(people.minBy { it.age })
    println(people.maxBy { it.age })

    val sumLambda: (x: Int, y: Int) -> Int = {x,y-> x + y}
    val testLambda = { println("Hello")}

    println(sumLambda(4,5))
    run { println(42)}
    testLambda.invoke()
    val result2 = sumLambda(4,9)

    println(result2)

    people.maxBy { x: Person -> x.age }

    val names = people.joinToString(" ",
        transform = {x: Person -> x.name})

    people.joinToString("! ") {x -> x.name  }

    val names2 = people.joinToString(", ") { it.name }

    println(names)
    println(names2)

    val sum = {x: Int, y: Int ->
        println("Computing the sum of $x and $y...")
        x + y
    }

    println(sum(10,23))

    val errors = listOf<String>("403 Forbidden", "404 Not Found")
    printMessageWithPrefix(errors, "Error:")

    val responses = listOf("200 OK", "418 I'm a teapot",
    "500 Internal Server Error", "404 big error", "402 bad gateway")
    printProblemCounts(responses)

    val myButton = Button()
    println(countClickingOnButton(myButton))
    println(countClickingOnButton(myButton))
    println(countClickingOnButton(myButton))

    val getAge = Person::age
    val getAge2 = {person: Person -> person.age}
    println(people.maxBy(getAge))

    run(::salute)

    val createPerson = ::Person
    val p = createPerson("Bobby", 50)
    println(p)

    val predicate = Person::isAdult

    println(p.isAdult())

    val listOfNumbers = setOf(1,2,2,3,3,4,5,6,7,8)
    println(listOfNumbers.filter { it % 2 == 0 })

    println(people.filter { it.age < 30 })

    var testMap = listOfNumbers.map {it * it}
    println(testMap)
    println(people.map {it.name})
    println(people.map (Person::name))

    println(people.filter { it.age > 30 }.map (Person :: name))

    println(people.filter { it -> it.age == people.maxBy { it.age }.age})

    val maxAge = people.maxBy ( Person ::age ).age
    people.filter {it.age == maxAge}

    val number = mapOf<Int, String>(0 to "zero", 1 to "one")
    println(number.mapValues { it.value.uppercase(Locale.getDefault()) })

    val canBeInClub27 = {p: Person -> p.age <= 27 }
    println(people.all(canBeInClub27))
    println(people.any(canBeInClub27)) //same as !all

    println(!listOfNumbers.all {it == 3})
    println(listOfNumbers.any {it != 3})

    println(people.count(canBeInClub27))

    println(people.find(canBeInClub27))
    println(people.firstOrNull(canBeInClub27))
    // find synonym is firstOrNull

    val testGroupBy = people.groupBy { it.age }
    //result of a groupBy operation is a map
    // key is the elements by which collection was grouped
    // value - objects stored in List

    val list2 = listOf("a", "ab", "b")
    println(list2.groupBy ( String :: first ))

    val books: List<Book> = listOf(
        Book("The Legend of Sleepy Hollow", listOf("Washington Irving")),
        Book ("Monday Begins on a Saturday", listOf("Boris Strygatsky", "Arkady  Strugatsky" )),
        Book ("Mort", listOf("Terry Pratchett")))

    println(books.flatMap { it.authors }.toSet())

    // flatMap transforms each element to a collection according to a lambda or member reference (or maps),
    // and then it combines several list into one (or flatten)

    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })

    val notASequence = people.map(Person::name).filter { it.startsWith("A") }
    println(notASequence)

    val testSequence2 =
        people.asSequence()
        .map(Person :: name)
        .filter {it.startsWith("A")}
        .toList()

    println(testSequence2)

    listOf(1,2,3,4).asSequence()
        .map {print ("map ($it) "); it * it}
        .filter {print("filter ($it) "); it % 2 == 0}
        .toList()
    println()

    println(listOf(1,2,3,4).asSequence()
        .map { it * it}.find {it > 3})

    val sequence1 = println(people.asSequence()
        .map(Person::name)
        .filter {it.length < 4}.toList())

    val sequence2 = println(people.asSequence()
        .filter {it.name.length < 4}
        .map (Person::name).toList())

    val naturalNumbers = generateSequence(0) {it + 1}
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())

    fun File.isInsideHiddenDirectory() =
        generateSequence(this) {it.parentFile}.any {it.isHidden}

    val file = File ("/.HiddenDir/a.txt")
    println(file.isInsideHiddenDirectory())

    




}
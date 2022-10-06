import kotlin.math.max

data class Person (val name: String, val age: Int) {


}

fun findTheOldest(people: List<Person>){
    var maxAge = 0
    var theOldest: Person? = null
    for (person in people){
        if (person.age > maxAge){
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

class Button(var pressCounting: Int = 0){

    fun onClick(){
        println("Click!")
        pressCounting++
    }

}
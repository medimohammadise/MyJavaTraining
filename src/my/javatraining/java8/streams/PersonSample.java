package my.javatraining.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class PersonSample {
	public static List<Person> createPeople() {
		return Arrays.asList(new Person("Sara", 22, Gender.FMALE), new Person("Mohsen", 33, Gender.FMALE),
				new Person("Bill", 33, Gender.MALE), new Person("Jill", 12, Gender.MALE),
				new Person("jack", 2, Gender.MALE), new Person("Jack", 2, Gender.MALE)

		);
	}
   
	public static List<String> getOnlyThreeFmalesUpperNamesOlderThan1(List<Person> people, int ageLimit) {
		return people.stream().filter(person -> person.getAge() > ageLimit).map(person -> person.getName())
				.map(name -> name.toUpperCase()).limit(3).collect(Collectors.toList());

	}
	public static String getFirstFmalesUpperNamesOlderThan1(List<Person> people, int ageLimit) {
		return people.stream().filter(person -> person.getAge() > ageLimit).map(person -> person.getName())
				.map(name -> name.toUpperCase()).findFirst().orElse("Not Found");

	}


	public static List<String> getAllFmalesUpperNamesOlderThan2(List<Person> people, int ageLimit) {
		return people.stream().filter(person -> person.getAge() > ageLimit).map(Person::getName)
				.map(String::toUpperCase).collect(Collectors.toList());

	}

	public static void printAllmales(List<Person> people) {
		people.stream().filter(person -> person.getGender() == Gender.MALE).forEach(System.out::println);

	}

	public static List<String> getAllmales(List<Person> people) {
		return people.stream().filter(person -> person.getGender() == Gender.MALE).map(person -> person.getName())
				.map(name -> name.toUpperCase()).collect(Collectors.toList());

	}

	public static int getTotalAges1(List<Person> people) {
		// start from 0 add your own age and pass to the next
		return people.stream().map(Person::getAge).reduce(0, (sum, age) -> sum + age);

	}

	public static int getTotalAges2(List<Person> people) {
		// start from 0 add your own age and pass to the next
		return people.stream().map(Person::getAge).reduce(0, Integer::sum);

	}

	public static int getTotalAges3(List<Person> people) {
		// Java is static type and We have to change mapp function to use sum , becuase
		// you can not suddenly call derived class function is
		// has not come in the base class
		return people.stream().mapToInt(Person::getAge).sum();

	}

	public static Optional<Person> getOldestPerson(List<Person> people) {
		// Java is static type and We have to change mapp function to use sum , becuase
		// you can not suddenly call derived class function is
		// has not come in the base class
		return people.stream().max((person1, person2) -> (person1.getAge() > person2.getAge() ? 1 : -1));

	}

	public static Optional<Person> getYoungestPerson(List<Person> people) {
		// Java is static type and We have to change mapp function to use sum , becuase
		// you can not suddenly call derived class function is
		// has not come in the base class
		return people.stream().min((person1, person2) -> (person1.getAge() > person2.getAge() ? 1 : -1));

	}

	public static long countNonAdults(List<Person> people) {
		// Java is static type and We have to change mapp function to use sum , becuase
		// you can not suddenly call derived class function is
		// has not come in the base class
		return people.stream().filter(person -> person.getAge() < 18).count();

	}

	public static List<String> getAllFmalesUpperNamesNotRecommended(List<Person> people, int ageLimit) {
		// Thread safety is on question
		List<String> names = new ArrayList<String>();
		people.stream().filter(person -> person.getAge() > ageLimit).map(person -> person.getName())
				.map(name -> name.toUpperCase()).forEach(name -> names.add(name));
		return names;

	}
	
	public static Set<String> printAllFmalesSet(List<Person> people) {
		return people.stream().filter(person -> person.getGender() == Gender.FMALE).map(Person::getName).collect(toSet());

	}

	public static List<String> printAllFmalesList(List<Person> people) {
		return people.stream().filter(person -> person.getGender() == Gender.FMALE).map(Person::getName).collect(toList());

	}
	
	public static Map<String,Person> printAllFmalesMap(List<Person> people) {
		return people.stream().filter(person -> person.getGender() == Gender.FMALE).collect(toMap(
				  person-> person.getName()+person.getAge(),
				  person->person
				)
				);

	}
	
	public static Map<String,List<Person>> printAllFmalesMapGroupBy(List<Person> people) {
		return people.stream().collect(
			  groupingBy(Person::getName)
				);

	}
	
	public static Optional<Person> findThefirstpersonWhosenameis4andoldertahn(List<Person> people,int ageLimit) {
		return people.stream().filter(person->person.getName().length()==4).filter(person->person.getAge()>ageLimit).findFirst();
	}
	public static Optional<Person> findThefirstpersonWhosenameis4andoldertahnPerformance1(List<Person> people,int ageLimit) {
		return people.stream().filter(PersonSample::isNameEqualto4).filter(person->person.getAge()>ageLimit).findFirst();
	}
	
	public static List<Person> findThefirstpersonWhosenameis4andoldertahnPerformance2(List<Person> people,int ageLimit) {
		return people.stream().filter(PersonSample::isNameEqualto4).filter(person->person.getAge()>ageLimit).collect(toList());
	}
	
	public static boolean isNameEqualto4(Person person) {
		System.out.println("calling isNameEqualto4 for "+person.getName());
		return (person.getName().length()==4);
	}
	
	public static void generateInifinteCollection() {
		//can be converted to parallel
		Stream.iterate(1,e->e+1).filter(e->e%2==0).limit(10).forEach(System.out::println);
	}
	
	public static List<Person> sortByAllAge(List<Person> people) {
		return people.stream().sorted((person1,person2)->
		(person1.getAge()<person2.getAge()?1:-1)).collect(toList());
	}
	
	public static List<Person> sortByAllAgeJava7(List<Person> people) {
		 Collections.sort(people, new Comparator<Person>(){

			@Override
			public int compare(Person person1, Person person2) {
				
				return person1.getAge()-person2.getAge();
			}
			
		});
		 return people;
	}


	public static Set<Person> stortByAgeAndCollectToSet(List<Person> people){
		Function<Person,Integer> byAge=Person::getAge;
		return people.stream().sorted(comparing(byAge)).collect(toSet());
	}
	public static Set<Person> stortByNameAndCollectToSet(List<Person> people){
		Function<Person,String> byAge=Person::getName;
		return people.stream().sorted(comparing(byAge)).collect(toSet());
	}
	public static void main(String[] args) {
		List<Person> people = createPeople();
		/*System.out.println(getOnlyThreeFmalesUpperNamesOlderThan1(people, 20));
		System.out.println(getFirstFmalesUpperNamesOlderThan1(people,90));
		System.out.println(getAllFmalesUpperNamesOlderThan2(people, 18));
		printAllmales(people);
		System.out.println(getAllmales(people));
		System.out.println(getTotalAges1(people));
		System.out.println(getTotalAges2(people));
		System.out.println(getTotalAges3(people));
		System.out.println(getOldestPerson(people));
		System.out.println(getYoungestPerson(people));
		System.out.println(countNonAdults(people));
		
		
		
		System.out.println(getAllFmalesUpperNamesNotRecommended(people,18));
		
		System.out.println(printAllFmalesSet(people));
		System.out.println(printAllFmalesList(people));
		
		System.out.println(printAllFmalesMap(people));
		
		System.out.println(printAllFmalesMapGroupBy(people));
		
		System.out.println(findThefirstpersonWhosenameis4andoldertahn(people,12));
		System.out.println(findThefirstpersonWhosenameis4andoldertahnPerformance1(people,12));
		
		System.out.println(findThefirstpersonWhosenameis4andoldertahnPerformance2(people,12));
		
		generateInifinteCollection();
		
		Function<Person,Integer> function=person->person.getAge();
		System.out.println("person's age "+ function.apply(new Person("test",12,Gender.MALE)));
		
		Function<Person,Integer> functionMethodReference=Person::getAge;
		System.out.println("person's age "+ functionMethodReference.apply(new Person("test",12,Gender.MALE)));
		System.out.println("**** Sorted List ****");
		System.out.println(sortByAllAge(people)); 
		System.out.println(sortByAllAgeJava7(people)); */
		System.out.println(stortByAgeAndCollectToSet(people)); //removes duplicate ones in age
		System.out.println(stortByNameAndCollectToSet(people)); //removes duplicate ones in age but the output is different
	}
}

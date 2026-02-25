package movie_rental_system;

public class Movie {
	String title;
	int year;
	String genre;
	String director;
	String studio;
	int stock;
	double price;
	
	Movie(String title, int year, String genre, String director, String studio, int stock, double price) {
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.director = director;
		this.studio = studio;
		this.stock = stock;
		this.price = price;
	}
}

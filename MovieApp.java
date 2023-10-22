import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;

public class MovieApp {
    Scanner sc = new Scanner(System.in);

    final float childTicket = 1000, adultTicket = 1200;
    float bill ;
    String name, email, exp_date;         
    int  cvv, phone;
    long cardnum;
    void print(){
        System.out.println();
        System.out.println("Hello, Welcome to the VCC Multiplex");
        System.out.println("----Movie Ticket Booking System----");
        System.out.println("-----------------------------------\n");

        System.out.println("1 -   Bollywood");
        System.out.println("2 -   Hollywood");
        System.out.println("3 -   Kollywood");
        System.out.println("4 -   Exit \n");

        System.out.println("Select which type of Movie you are interested in:");
    }
    float calculateBill(int seat, int child){
        float adult_bll =  adultTicket * (seat-child);
        float child_bill = childTicket * child;
        bill =  adult_bll + child_bill;
        return bill;

    }
    void getUserDetails(){          //Input user details
        while(true){
            try{
                System.out.print("Enter name : ");
                name = sc.next();
                System.out.print("Enter email address : ");
                email = sc.next();
                System.out.print("Enter Phone number :");
                phone = sc.nextInt();
                System.out.print("Enter Debit Card Number : ");
                cardnum = sc.nextLong();
                System.out.print("Enter CVV : ");
                cvv = sc.nextInt();
                System.out.print("Enter Expire Date : ");
                exp_date = sc.next();
                break;                           //Stop if inputs are successfull
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input, Please add valid value ");
                sc.nextLine();
            }
        }
    }
    public static void main(String[] args) {
        int type, mov, selecthall, selecttime;
        String mvName;
        MovieApp mobj = new MovieApp();
        Movie movieobj = new Movie();
        CinemaHalls cinobj = new CinemaHalls();
        
        while(true){
            try{
                mobj.print();
                type = mobj.sc.nextInt();

                switch (type){                                      //Select movie type
                    case 1:
                        movieobj.getMovie(movieobj.bMovie);
                        break;

                    case 2:
                        movieobj.getMovie(movieobj.hMovie);
                        break;

                    case 3:
                        movieobj.getMovie(movieobj.kMovie);
                        break;
                    case 4:
                        System.out.println("Program Stopped !");
                        System.out.println("Thank you for using !! ");
                        System.exit(1);
                    default:
                        System.out.println("Invalid Selection !");
                        break;
                }
                mov = mobj.sc.nextInt();
                movieobj.getDetails(mov-1);               //Print movie details
                mvName = movieobj.getMovie();

                cinobj.printCinemaHalls();                //Print halls
                selecthall = mobj.sc.nextInt();
                cinobj.setHall(selecthall);
                cinobj.setHallNum(selecthall);

                cinobj.printShowtimes();                  //Print times
                selecttime = mobj.sc.nextInt();
                cinobj.setTime(selecttime);
                break;
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Invalid Selection, please do valid selections...");
                System.out.println(e);
            }
        }
        System.out.println("Enter No of Seats you need : ");
        int seats = mobj.sc.nextInt();
        while(cinobj.bookSeats(seats)!=true){
            cinobj.printCinemaHalls();
            selecthall = mobj.sc.nextInt();
            cinobj.setHallNum(selecthall);
            cinobj.setHall(selecthall);
        }
        
        System.out.println("How many tickets for Children ? ");
        int child = mobj.sc.nextInt();

        mobj.calculateBill(seats, child);
        
        
        System.out.println("Enter Details for Confirmation & Payment : ");
        mobj.getUserDetails();
        User userobj = new User(mobj.name, mobj.email, mobj.phone,mobj.cardnum, mobj.cvv,mobj.exp_date);
        Bill billobj = new Bill(mobj.name, mobj.email, mobj.phone,mobj.cardnum, mobj.cvv,mobj.exp_date);
        while(userobj.checkCard()!=true){
            System.out.println("Enter Debit Card Number : ");
            long cardnum = mobj.sc.nextLong();
            userobj.setCard(cardnum);
        }
        billobj.printSummaryBill(mvName,cinobj.getTime(),cinobj.getHall(),seats,child, mobj.childTicket, mobj.adultTicket, mobj.bill);
    }
}

class Movie{
    private String selectedMovie[][];
    private String movieName;

    String bMovie[][] = {{"Animal", "Dunki", "Fighter"},
                              {"Action", "Comedy", "Action"},
                              {"Ranbeer Capoor", "Shahrukh Khan", "Hrithik Roshan" },
                              {"Rashmika Mandanna", "Taapsee Pannu", "Deepika Padukone"} };

    String hMovie[][] = {{"Joker", "Deadpool 3", "Reptile"},
                        {"Musical Thriller", "Action", "Crime"},
                        {"Joaquin Phoenix","Ryan Reynolds", "Benicio del toro"},
                        {"Lady Gaga", "Morena Baccarin","Alicia Silverstone"}};

    String kMovie[][] = {{"Captain Miller","Leo", "Japan"},
                        {"Action", "Action", "Comedy"},
                        {"Dhanush","Vijay", "Karthi"},
                        {"Priyanka Mohan","Trisha","Anu Emmanuel"}};

    void getMovie(String a[][]){
        System.out.println();
        System.out.println("Here is the available film list for your selected category");
        for(int i=0; i<a[0].length; i++){
            System.out.println((i+1)+" - " + a[0][i]);
        }
        System.out.println();
        System.out.println("Select Movie: ");

        selectedMovie = a;
    }
    public void getDetails(int x){
        String []array = {"Movie  ","Genre  ","Actor  ","Actress"};
        for (int j = 0; j < selectedMovie.length; j++) {
                System.out.println(array[j] +" : " + selectedMovie[j][x] );
        }
        System.out.println();
        this.movieName = selectedMovie[0][x];
        
    }
    
    public String getMovie(){
        return movieName;
    }
}
class CinemaHalls{
    String showtimes [] = {"10:30 AM","2:30 PM","9:30 PM"};
    String cinema[] = {"C1","C2","C3","C4"};                          //Cinema Halls
    int availableSeats [] ={22,3,45,8};                           //Available seats
    private String time, hall;
    private int hallNumber;

    void printShowtimes(){
        System.out.println("Select Show Time: ");
        for(int i=0; i<showtimes.length ; i++){
            System.out.print((i+1) + " : ");
            System.out.println(showtimes[i]);
        }
        System.out.println();
    }
    public void setTime(int time){
        this.time = showtimes[time-1];
    }
    public String getTime(){
        return time;
    }
    public void setHall(int hall){
        this.hall = cinema[hall-1];
    }
    public String getHall(){
        return hall;
    }
    public void setHallNum(int hallNum){
        this.hallNumber =hallNum; 
    }
    public int getHallNum(){
        return hallNumber;
    }
    void printCinemaHalls(){
        System.out.println("Select Hall: ");
        for(int i=0; i<cinema.length; i++){
            System.out.print((i+1) + " : ");
            System.out.println(cinema[i]);
        }
        System.out.println();
    }
    public boolean bookSeats(int seat){
        if(seat > (availableSeats[getHallNum()-1])){
            System.out.println("Don't have enough seats. Please check another Hall ");
            return false;
        }
        else{
            System.out.println("Seats Available");
            return true;
        }
    }

}
 class User{

    public String name, email;
    public int phone;
    private int cvv ;
    private long cardnum;
    private String exp_date;

    User(String name,String email, int phone,long cardnum,int cvv, String exp_date){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cardnum = cardnum;
        this.cvv = cvv;
        this.exp_date = exp_date;
        
    }
    public void setCard(long cardnum){
        this.cardnum = cardnum;
    }
    //Checking the length of the card
    public boolean checkCard(){
        String card = Long.toString(cardnum);
        if(card.length() == 16){
            System.out.println("Card Added Successfully ! \n");
            return true;
        }
        else{
            System.out.println("Invalid. Card number must have 16 digits");
            return false;
        }
    }
}

class Bill extends User{

    Bill(String name,String email, int phone,long cardnum,int cvv, String exp_date){
        super(name, email, phone, cardnum, cvv, exp_date);
    }
    //Getting current date and time 
    LocalDateTime now = LocalDateTime.now();

    public void printSummaryBill(String mvName, String showTime, String hall,int seats, int child, float childTicket, float adultTicket, float bill){
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t\t      VCC Multiplex      \t\t\t");
        System.out.println("\t\t  Main Street, Vavuniya     \n");

        System.out.println("Here is the Ticket for "+ mvName.toUpperCase() + " Movie \n");
        
        System.out.println("Customer name  : " + super.name);
        System.out.println("Email address : "+ super.email);
        System.out.println("Phone number : "+ super.phone);
        System.out.println("Order date & time : " + now);
        System.out.println("Show time : " + showTime);
        System.out.println("Cinema Hall : " + hall);
        System.out.println();

        System.out.println("No of Adult tickets : "+ (seats-child));
        System.out.println("No of Child tickets : "+ child);
        System.out.println();

        System.out.println("For Children : "+ childTicket +" x " + child + " = " + (childTicket*child));
        System.out.println("For Adults   : "+ adultTicket +" x " + (seats-child) + " = " + (adultTicket *(seats-child)));
        System.out.println();

        System.out.println("Total is     :"+ "\t \t  = " +(bill));

        System.out.println();

        System.out.println("!!! NO OUTSIDE FOOD / BEVARAGE !!!");
        System.out.println("!!! NO REFUND / EXCHANGE !!!");
        System.out.println("!!! Valid only for the issued screening !!!");
        System.out.println();

        System.out.println("You can contact us for further details!");
        System.out.println("Phone - 0702728617");
        System.out.println("FOTS 20/21 ");
        System.out.println("University of Vavuniya");
        System.out.println("\nThank you! ");
        System.out.println("------------------------------------------------------------------");
    }
}
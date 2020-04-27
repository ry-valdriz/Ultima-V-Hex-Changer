import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
	
	//offsets of all character stats
	static long[] mankikkoStats = {14,16,15,18, 20, 22 };
	static long[] shaminoStats = {46, 48, 47, 50, 52, 54};
	static long[] ioloStats = {78,80, 79, 82, 84, 86 };
	static long[] mariahStats = {110, 112, 111, 114, 116, 118};
	static long[] geoffreyStats = {142, 144, 143, 146, 148, 150};
	static long[] jaanaStats = {174, 176, 175, 178, 180, 182};
	static long[] juliaStats = {206, 208, 207, 210, 212, 214};
	static long[] dupreStats = {238, 240, 239, 242, 244, 246};
	
	//offset for items
	static long[] itemOffsets = {516,518, 523, 519, 536, 522, 576};   
	
	public static void main(String[] args) throws IOException {
		//initialize scanner object
		Scanner in = new Scanner(System.in);
		
		//create file object
		File ultimaSave = new File("C:\\OLDGAMES\\Ultima_5\\Ultima_5\\SAVED.GAM");
		
		//variables for choices
		int selection;
		int items;
		int stat; //index for offset array
		int chars;
		boolean loop = true;
		
		System.out.println("---------------------------");
		System.out.println("|ULTIMA V GAME FILE EDITOR|");
		System.out.println("---------------------------" + "\n");
		
		
		while(loop){
		//user input
		System.out.println("What do you wish to do?:");
		System.out.println("1.)Edit characters");
		System.out.println("2.)Edit items");
		System.out.println("3.)exit");
		
		selection = in.nextInt();
		
		//SORRY FOR THE MESSY PROGRAMMING
		switch(selection){
		case 1://modify character stats
			chars = chooseCharacter();
			
			//validate
			if(chars > 9 || chars < 1){
				while(chars > 9 || chars < 1){
				System.out.println("invalid input");
				chars = chooseCharacter();
				}
			}
			
			stat = chooseStat();
			
			//validate
			if(stat > 6 || stat < 1){
				while(stat > 6 || stat < 1){
				System.out.println("invalid input");
				stat = chooseStat();
				}
			}
			
			switch(chars){
			case 1: //mankikko stats
				changeValues(mankikkoStats[stat-1], ultimaSave);
				break;
			case 2: //shamino stats
				changeValues(shaminoStats[stat-1], ultimaSave);
				break;
			case 3: //iolo stats
				changeValues(ioloStats[stat-1], ultimaSave);
				break;
			case 4: //mariah stats
				changeValues(mariahStats[stat-1], ultimaSave);
				break;
			case 5: //geoffrey stats
				changeValues(geoffreyStats[stat-1], ultimaSave);
				break;
			case 6: //Jaana stats
				changeValues(jaanaStats[stat-1], ultimaSave);
				break;
			case 7: //julia stats
				changeValues(juliaStats[stat-1], ultimaSave);
				break;
			case 8: //Dupre stats
				changeValues(dupreStats[stat-1], ultimaSave);
				break;
			}
			
			break;
			
		case 2://edit party items
			items = chooseItem();
			
			changeValues(itemOffsets[items-1], ultimaSave);		
			break;
			
		case 3: //quit
			loop = false;
			break;
		default: 
			System.out.println("That's not a valid input!");
			break;
			
			
		}//end of main menu
		
		}// while loop
		
		
		
		System.out.println("\n" + "BYE BYE!!!");
		
		
	}
	
	/**
	 * allows me to store multi-byte numbers
	 * @param x a decimal number
	 * @return the decimal in hex
	 */
	public static byte[] intToByteArray(int x){
		byte[] output = new byte[4];
		
		output[0] = (byte)(x>>>24);
		output[1] = (byte)(x>>>16);
		output[2] = (byte)(x>>>8);
		output[3] = (byte)(x);
		
		return output;
	}
	
	/**
	 * character selection
	 * @return
	 */
	public static int chooseCharacter(){
		int choice;
		//get user input
		Scanner in = new Scanner(System.in);
		System.out.println("Enter what character you wish to modify?(type out number) ");
		System.out.println("1.)Mankikko");
		System.out.println("2.)Shamino");
		System.out.println("3.)Iolo");
		System.out.println("4.)Mariah");
		System.out.println("5.)Geoffrey");
		System.out.println("6.)Jaana");
		System.out.println("7.)Julia");
		System.out.println("8.)Dupre");
		
		choice = in.nextInt();
		
		return choice;
		
	}
	
	/**
	 * item selection
	 * @return index for item offset array
	 */
	public static int chooseItem(){
		int choice;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter what item count you wish to modify?(type out the number)");
		System.out.println("1.)Gold");
		System.out.println("2.)keys");
		System.out.println("3.)skull keys");
		System.out.println("4.)gems");
		System.out.println("5.)black badge");
		System.out.println("6.)magic carpets");
		System.out.println("7.)magic axes");
		
		choice = in.nextInt();
		
		return choice;
		
	}
	
	/**
	 * stat selection
	 * @return the index for the stat offset array
	 */
	public static int chooseStat(){
		int choice;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Which stat to change?:");
		System.out.println("1.)Str");
		System.out.println("2.)Int");
		System.out.println("3.)Dex");
		System.out.println("4.)HP");
		System.out.println("5.)Max HP");
		System.out.println("6.)Exp");
		
		
		choice = in.nextInt();
		
		return choice;
		
	}
	
	/**
	 * where the magic happens. changes hex values in the game file
	 * @param pos position of pointer
	 * @param ultimaSave game file
	 * @throws IOException
	 */
	public static void changeValues( long pos, File ultimaSave ) throws IOException{
				
				
				//check file
				if(!ultimaSave.exists()){
					System.out.println("File cannot be found");
					System.exit(1);
				}
				
				//initialize scanner object
				Scanner in = new Scanner(System.in);
				
				//user input for strength
				//System.out.println("Ultima V editor" + "\n");
				System.out.print("Enter desired value: ");
				int statValue;
				statValue = Integer.parseInt(in.nextLine());
				
				//write
				RandomAccessFile ultRAF = new RandomAccessFile(ultimaSave, "rw");
				ultRAF.seek(pos);
				ultRAF.write(intToByteArray(Integer.reverseBytes(statValue)));
				
				ultRAF.close();
				System.out.println("Done");
	}
	
	/*
	public static void changeGold() throws IOException{
		//create file object
		File ultimaSave = new File("C:\\OLDGAMES\\Ultima_5\\Ultima_5\\SAVED.GAM");
		
		//check file
		if(!ultimaSave.exists()){
			System.out.println("File cannot be found");
			System.exit(1);
		}
		
		//initialize scanner object
		Scanner in = new Scanner(System.in);
		
		//user input for strength
		//System.out.println("Ultima V editor" + "\n");
		System.out.print("Enter the value of gold for your character: ");
		int gold;
		gold = Integer.parseInt(in.nextLine());
		
		//write
		RandomAccessFile ultRAF = new RandomAccessFile(ultimaSave, "rw");
		ultRAF.seek(516);
		ultRAF.write(intToByteArray(Integer.reverseBytes(gold)));
		
		ultRAF.close();
		System.out.println("Done");
	}  */
	
	

}

import java.io.File;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
public class Chatbot {
	private static final boolean TRACEMODE = false;
	static String bot_name = "super";
	
	public static  void main(String[] args)
	{
		try
		{
			String resources_path = getResourcesPath();
			System.out.println(resources_path);
			MagicBooleans.trace_mode = TRACEMODE;
			Bot bot = new Bot("super", resources_path);
			Chat chat_session = new Chat(bot);
			bot.brain.nodeStats();
			String text_line = "";
			
			while(true)
			{
				System.out.println("Human:");
				text_line = IOUtils.readInputTextLine();
				if(text_line==null || text_line.length()<1)
				{
					System.exit(0);
				}
				else if(text_line.equals("wq"))
				{
					bot.writeQuit();
					System.exit(0);
				}
				else
				{
					String request = text_line;
					if(MagicBooleans.trace_mode)
					{
						System.out.println("State="+ request + ":THAT" + 
								((History)chat_session.thatHistory.get(0)).get(0)
								+"Topic="+ chat_session.predicates.get("topic"));
					}
					String response = chat_session.multisentenceRespond(request);
					while(response.contains("&<"))
						response = response.replace("&<","<");
					while(response.contains("&>"))
						response = response.replace("&>",">");
					System.out.println("robot:"+response);
					
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static String getResourcesPath()
	{
		File cur_dir= new File(".");
		String file_path  = cur_dir.getAbsolutePath();
		file_path = file_path.substring(0,file_path.length()-2);
		System.out.println(file_path);
		String resources_path = file_path + File.separator + "src" + 
				File.separator + "main" + File.separator + "resources";
		return resources_path;
		
	}
}

package root.ffmpeg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class MergeVideoMp3 {
	
private String ffmpegEXE;
	
	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}
	
	public void convertor(String videoInputPath, String mp3InputPath,
			double seconds, String videoOutputPath) throws Exception {
		//		ffmpeg.exe -i {mp}.mp4 -i {mp}.mp3 -t {seconds} -y {out}.mp4
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		
		command.add("-i");
		command.add(videoInputPath);
		
		command.add("-i");
		command.add(mp3InputPath);
		
		command.add("-t");
		command.add(String.valueOf(seconds));
		
		command.add("-y");
		command.add(videoOutputPath);
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line = "";
		while ( (line = br.readLine()) != null ) {
		}
		
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
		
	}
	
	public static void main(String[] args) {
		String str = "abc.jpg";
		String[] split = str.split("\\.");
		for (String s : split) {
			System.out.println(s);	// abc,jpg
		}
		
		String[] joinArray = {"a","b","c","d"};
		String j = Joiner.on(".").join(joinArray);
		System.out.println(j); // "a.b.c.d"
		
		Map<String, String> joinMap = Maps.newHashMap();
		joinMap.put("a", "1");
		joinMap.put("b", "2");
		joinMap.put("c", "3");
		String m = Joiner.on("&")
		.useForNull("")
		.withKeyValueSeparator("=")
		.join(joinMap);
		System.out.println(m);	// a=1&b=2&c=3
		
		String splitStr = "q,w,e,rty";
		List<String> splitToList = Splitter.on(",").trimResults().omitEmptyStrings()
							.splitToList(splitStr);
		for (String sp: splitToList) {
			System.out.println(sp); // q,w,e,rty
		}
	}
}

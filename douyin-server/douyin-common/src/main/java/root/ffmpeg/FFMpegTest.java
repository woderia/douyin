package root.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.Lists;


public class FFMpegTest {

	private String ffmpegExE;
	
	public FFMpegTest(String ffmpegExE) {
		super();
		this.ffmpegExE = ffmpegExE;
	}

	public void convertor(String videoInputPath, String videoOutputPath) throws IOException {
		// ffmpeg.exe -i vd.mp4 winter.avi
		List<String> command = Lists.newArrayList();
		command.add(ffmpegExE);
		command.add("-i");
		command.add(videoInputPath);
		command.add(videoOutputPath);
		for (String c : command) {
			System.out.println(c);
		}
		// 调用cmd
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		// 关流
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line = "";
		while((line = br.readLine()) != null) {
		}
		if (br != null ) {
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
		FFMpegTest ffMpegTest = new FFMpegTest("D:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffMpegTest.convertor("D:\\ffmpeg\\bin\\vd.mp4", "D:\\ffmpeg\\bin\\winter.avi");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

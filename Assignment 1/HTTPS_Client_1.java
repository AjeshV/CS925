import java.io.*;
import java.net.*;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import java.net.HttpURLConnection;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.security.cert.X509Certificate;

#stackoverflow
class HTTPSClient {
 static int numberOfAttempts = 4;
 static int portHTTPS;
 public static void main(String[] args) throws Exception 
 {
	double[] timeHTTPS = new double[numberOfAttempts];
	String host = "www.stackoverflow.com";
	portHTTPS = 8081;
	for (int i = 0; i < numberOfAttempts; i++) {
 	 timeHTTPS[i] = sendGetSecure(host);
	 System.out.println("HTTPS#" + (i + 1) + ":    " + timeHTTPS[i]);
	}
	System.out.println("Mean time:"+ getMean(timeHTTPS));
	System.out.println("Standard deviation:"  + getStdDev(timeHTTPS));
 }
	static double sendGetSecure(String IP) throws Exception {
	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	return null;
	}
	public void checkClientTrusted(X509Certificate[] certs, String authType) {
	}
	public void checkServerTrusted(X509Certificate[] certs, String authType) {
	}
	} };
SSLContext sc = SSLContext.getInstance("SSL");
sc.init(null, trustAllCerts, new java.security.SecureRandom());
HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
HostnameVerifier allHostsValid = new HostnameVerifier() {
public boolean verify(String hostname, SSLSession session) {
return true;
}
};
 HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
 URL url = new URL("https://" + IP);
 double time, time1, time2;
 time1 = System.nanoTime();
 HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
 con.setRequestMethod("GET");
 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
 String inputLine;
 StringBuffer response = new StringBuffer();
 while ((inputLine = in.readLine()) != null) {
	 response.append(inputLine);
	 }
  in.close();
  con.disconnect();
  time2 = System.nanoTime();
  time = (time2 - time1) / Math.pow(10, 6);
  return time;
}
 static double getMean(double[] data) {
 double sum = 0.0;
 for (double a : data)
	 sum += a;
 return (sum / data.length);
	}
	static double getVariance(double[] data) {
	double mean = getMean(data);
	double temp = 0;
	for (double a : data)
	temp += (mean - a) * (mean - a);
	return temp / data.length;
}
static double getStdDev(double[] data) {
	return Math.sqrt(getVariance(data));
}

}

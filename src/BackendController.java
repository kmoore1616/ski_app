import java.io.*;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BackendController {
    private static PrintWriter out;
    private static Socket socket;
    public BackendController() throws IOException {
        socket = new Socket("127.0.0.1", 4241);
        out = new PrintWriter(socket.getOutputStream(), true);
    }


    public static void sendDbCode(int code){
        out.print(code);
        out.flush();
    }

    public static int getDbCode() {
        try {
            return socket.getInputStream().read();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static int download_db(String login) throws IOException {
        InputStream in = socket.getInputStream();
        int bytes_read;

        out.print(login);
        out.flush();

        int response = socket.getInputStream().read();

        if(response == '3'){
            System.out.println("Server error...");
        }else if(response == '0'){
            System.out.println("login failed");
            return 0;
        }else if(response=='1') {
            System.out.println("Login success");
            byte[] input_dat = new byte[2048];
            bytes_read = socket.getInputStream().read(input_dat);
            int i = 0;

            if (bytes_read > 0) {
                while (input_dat[i] != '|') {
                    i++;
                }
            }else{
                System.out.println("server error");
                return -1;
            }
            String sizeString = new String(input_dat, 0, i).trim();
            int file_size = Integer.parseInt(sizeString);
            System.out.println("File size: " + file_size);

            byte[] rawDB = new byte[file_size + 10];

            int totalBytesCollected = 0;

            // --- STEP C: Copy the "Leftover" Data from the First Read ---
            // Data starts AFTER the delimiter (index + 1)
            int dataStartIndex = i + 1;
            int bytesInFirstPacket = bytes_read - dataStartIndex;

            if (bytesInFirstPacket > 0) {
                System.arraycopy(input_dat, dataStartIndex, rawDB, 0, bytesInFirstPacket);
                totalBytesCollected += bytesInFirstPacket;
            }

            while (totalBytesCollected < file_size) {
                int result = in.read(rawDB, totalBytesCollected, bytes_read - totalBytesCollected);
                if (result == -1) break; // End of stream
                totalBytesCollected += result;
            }

            System.out.println("Download Complete! " + totalBytesCollected);

            try (FileOutputStream fos = new FileOutputStream("database.db")) {
                ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(rawDB));
                ZipEntry entry = zis.getNextEntry();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] zip_buff = new byte[2048];
                int len;
                while((len = zis.read(zip_buff)) > 0){
                    bos.write(zip_buff, 0, len);
                }
                zis.closeEntry();
                fos.write(bos.toByteArray());
                return 1;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}

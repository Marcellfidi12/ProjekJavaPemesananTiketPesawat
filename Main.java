import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner userOption = new Scanner(System.in);
        String pilihanPengguna;
        boolean lanjutkan = true;

        while (lanjutkan){
            clearScreen();
            System.out.println("==============================================");
            System.out.println("====== Aplikasi Pemesanan Tiket Pesawat ======");
            System.out.println("================= Kelompok 1 =================");
            System.out.println("==============================================\n");
            System.out.println("1.\tLogin");
            System.out.println("2.\tRegister");
            System.out.println("3.\tCari Penerbangan");
            System.out.println("4.\tJadwal Penerbangan");
            System.out.println("5.\tLogin Admin");

            System.out.print("Pilih : ");
            pilihanPengguna = userOption.next();

            switch(pilihanPengguna){
                case "1":
                    System.out.println("LOGIN");
                    loginAkun();
                    break;
                case "2":
                    System.out.println("DAFTAR");
                    buatAkun();
                    break;
                case "3":
                    System.out.println("CARI PENERBANGAN");
                    cariPenerbangan();
                    break;
                case "4":
                    System.out.println("JADWAL PENERBANGAN");
                    jadwalPenerbangan();
                    break;
                case "5":
                    System.out.println("PANEL ADMIN");
                    panelAdmin();
                    break;
                default:
                    System.out.println("Pilihan anda tidak ditemukan");
            }
            lanjutkan = YesOrNo("Apakah anda ingin melanjutkan? [y/n]");
        }
    }
    public static void  loginAkun() throws IOException {
        File database = new File("src/user.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        Scanner userOption = new Scanner(System.in);
        String username, password;

        System.out.print("Masukan username anda : ");
        username = userOption.nextLine();
        System.out.print("Masukan password anda : ");
        password = userOption.nextLine();

        String[] keywords = {username, password};

        boolean isExist = cekAkun(keywords, false);

        String pilihanPengguna;

        if (isExist){
            clearScreen();
            System.out.println("Anda berhasil login dengan username : " + username);
            System.out.println();
            System.out.println("==============================================");
            System.out.println("====== Beranda Pemesanan Tiket Pesawat =======");
            System.out.println("================= Kelompok 1 =================");
            System.out.println("==============================================\n");
            System.out.println("1.\tPesan Tiket Pesawat");
            System.out.println("2.\tLiat Jadwal Penerbangan");
            System.out.println("3.\tCari Penerbangan");

            System.out.print("Pilih : ");
            pilihanPengguna = userOption.next();

            switch(pilihanPengguna){
                case "1":
                    System.out.println("Pesan Tiket Pesawat");
                    pesanPenerbangan();
                    break;
                case "2":
                    System.out.println("Liat Jadwal Penerbangan");
                    jadwalPenerbangan();
                    break;
                case "3":
                    System.out.println("Cari Penerbangan");
                    cariPenerbangan();
                    break;
                default:
                    System.out.println("Pilihan anda tidak ditemukan");
            }
        } else {
            System.out.println("Akun tidak ada atau password salah,silahkan membuat akun terlebih dahulu.");
        }
        bufferedInput.close();
    }

    public static void buatAkun() throws IOException {
        FileWriter fileOutput = new FileWriter("src/user.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        Scanner userOption = new Scanner(System.in);
        String username, password;

        System.out.print("Buat username anda : ");
        username = userOption.nextLine();
        System.out.print("Buat password anda : ");
        password = userOption.nextLine();

        String[] keywords = {username, password};

        boolean isExist = cekAkun(keywords, false);

        if (!isExist){
            long nomorIdAkun = ambilIdAkun() + 1;

            boolean isTambah = YesOrNo("Apakah anda ingin akun tersebut? [y/n]");

            if(isTambah){
                bufferOutput.write(nomorIdAkun + "," + username + "," + password);
                bufferOutput.newLine();
                bufferOutput.flush();
                System.out.println("Berhasil membuat akun.");
            }
        } else {
            System.out.println("Akun sudah terdaftar,silahkan gunakan akun lain");
        }
        bufferOutput.close();
    }

    public static void panelAdmin() throws IOException {
        Scanner userOption = new Scanner(System.in);
        String pilihanPengguna;
        String useradmin = "admin";
        String passadmin = "admin123";

        boolean lanjutkan = true;

        String inputadmin,inputpadmin;
        System.out.print("Masukan Username Admin : ");
        inputadmin = userOption.nextLine();
        System.out.print("Masukan Password Admin : ");
        inputpadmin = userOption.nextLine();

        while (lanjutkan){
            if (inputadmin.equals(useradmin) && inputpadmin.equals(passadmin)) {
                clearScreen();
                System.out.println("Halaman Panel Admin\n");
                System.out.println("1.\tTambah Data Penerbangan");
                System.out.println("2.\tHapus Data Penerbangan");
                System.out.println("3.\tUpdate Data Penerbangan");
                System.out.println("4.\tLihat Data Pemesanan");

                System.out.print("Pilih : ");
                pilihanPengguna = userOption.next();

                switch (pilihanPengguna) {
                    case "1":
                        System.out.println("TAMBAH DATA PENERBANGAN");
                        tambahPenerbangan();
                        break;
                    case "2":
                        System.out.println("HAPUS DATA PENERBANGAN");
                        hapusPenerbangan();
                        break;
                    case "3":
                        System.out.println("UPDATE DATA PENERBANGAN");
                        updatePenerbangan();
                        break;
                    case "4":
                        System.out.println("LIHAT DATA PEMESANAN");
                        dataPemesanan();
                        break;
                    default:
                        System.out.println("Pilihan anda tidak ditemukan");
                }
            } else {
                System.out.println("Username Atau Password Admin Salah!!!");
            }
            lanjutkan = YesOrNo("Apakah anda ingin melanjutkan? [y/n]");
        }
    }
    public static void pesanPenerbangan() throws IOException {
        File database = new File("src/database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        File user = new File("src/user.txt");
        FileReader fileInputUser = new FileReader(user);
        BufferedReader bufferedInputUser = new BufferedReader(fileInputUser);

        File tempDB = new File("src/tempDB.txt");
        FileWriter fileOutput2 = new FileWriter(tempDB);
        BufferedWriter bufferOutput2 = new BufferedWriter(fileOutput2);

        FileWriter fileOutput = new FileWriter("src/pemesanan.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);


        Scanner userOption = new Scanner(System.in);
        System.out.print("Masukan asal dan tujuan yang anda inginkan : ");
        String cariString = userOption.nextLine();

        String[] keywords = cariString.split("\\s+");

        cekPenerbangan(keywords, true);

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan id penerbangan yang ingin dipesan : ");
        int updateNum = terminalInput.nextInt();

        String data = bufferedInput.readLine();
        int entryCounts = 0;

        StringTokenizer st = new StringTokenizer(data,",");

        terminalInput = new Scanner(System.in);
        System.out.print("Masukan Jumlah Penumpang : ");
        int jmlPenumpang = terminalInput.nextInt();
        String[] nama = new String[4];
        if(jmlPenumpang <= 4){
            for (int i = 0; i < jmlPenumpang; i++) {
                System.out.print("Masukkan nama penumpang ke - " + (i + 1) + " : ");
                nama[i] = terminalInput.next();
            }

            while (data != null){
                entryCounts++;

                st = new StringTokenizer(data,",");
                if (updateNum == entryCounts){
                    System.out.println("Penerbangan yang ingin anda pesan adalah :");
                    System.out.println("======================================");
                    System.out.println("ID           : " + st.nextToken());
                    System.out.println("Tanggal      : " + st.nextToken());
                    System.out.println("Pesawat      : " + st.nextToken());
                    System.out.println("Asal         : " + st.nextToken());
                    System.out.println("Tujuan       : " + st.nextToken());
                    System.out.println("Harga        : " + st.nextToken());

                    String stok = st.nextToken();
                    int stokTiket = Integer.parseInt(stok);

                    boolean isTrue = YesOrNo("Anda yakin ingin memesan penerbangan ini [y/n] ?");

                    String[] fieldData = {"tanggal","pesawat","asal","tujuan","harga","stok"};
                    String[] tempData = new String[6];

                    st = new StringTokenizer(data, ",");
                    String originalData = st.nextToken();

                    for (int i=0; i < fieldData.length; i++){

                        originalData = st.nextToken();
                        if (isTrue){
//                            terminalInput = new Scanner(System.in);
//                            System.out.print("\nMasukan " + fieldData[i] + " baru : ");
//                            tempData[i] = terminalInput.nextLine();
                        } else {
                            tempData[i] = originalData;
                        }

                    }

                    Scanner inputAkun = new Scanner(System.in);

                    System.out.println("Konfirmasi Pesan Penerbangan");
                    System.out.print("Masukan password anda : ");
                    String password = inputAkun.nextLine();

                    String[] keywordsAkun = {password};

                    boolean isExist = cekAkun(keywordsAkun, false);

                    if (isTrue){
                        if(isExist){
                            st = new StringTokenizer(data,",");
                            String kodeBooking = "TP00" + st.nextToken();
                            String tanggal_berangkat = st.nextToken();
                            String maskapai = st.nextToken();
                            String rute = st.nextToken() + "-" + st.nextToken();

                            st = new StringTokenizer(data,",");
                            System.out.println("======================================");
                            System.out.println("=========== BOARDING PASS ============");
                            System.out.println("======================================");
                            System.out.println("Kode Booking  : " + "TP00" + st.nextToken());
                            System.out.println("Tgl Berangkat : " + st.nextToken());
                            System.out.println("Maskapai      : " + st.nextToken());
                            System.out.println("Rute          : " + st.nextToken() + "-" + st.nextToken());
                            System.out.println("Jml Penumpang : " + jmlPenumpang);
                            System.out.println("Nama Penumpang: ");
                            for (int i = 0; i < jmlPenumpang; i++) {
                                System.out.println((i+1) + ". " + nama[i]);
                            }
                            String hargaSt = st.nextToken();
                            int harga_tiket = Integer.parseInt(hargaSt);
                            int total_bayar = jmlPenumpang * harga_tiket;
                            System.out.println("Nominal Tiket : " + "Rp. " + harga_tiket);
                            System.out.println("               ---------------");
                            System.out.println("Total Bayar   : " + "Rp. " + total_bayar);
                            bufferOutput.write(kodeBooking + "," + tanggal_berangkat + "," + maskapai + "," + rute + "," + nama[0] + "," + nama[1] + "," + nama[2] + "," + nama[3] + "," + jmlPenumpang + "," + harga_tiket + "," + total_bayar);
                            bufferOutput.newLine();
                            bufferOutput.flush();
                            int kurangtiket = stokTiket - jmlPenumpang;
                            st = new StringTokenizer(data,",");
                            bufferOutput2.write(st.nextToken() + "," + st.nextToken() + ","+ st.nextToken() + "," + st.nextToken() + ","+st.nextToken()+","+st.nextToken()+","+kurangtiket);
                        } else {
                            bufferOutput2.write(data);
                        }
                    } else {
                        System.out.println("Password anda salah,silahkan coba kembali.");
                    }
                } else {
                    bufferOutput2.write(data);
                }
                bufferOutput2.newLine();

                data = bufferedInput.readLine();
            }
        } else {
            System.out.println("Jumlah penumpang maksimal 4 orang");
        }

        bufferOutput2.flush();

        bufferedInput.close();
        bufferedInputUser.close();
        bufferOutput.close();
        bufferOutput2.close();

        database.delete();

        tempDB.renameTo(database);
    }
    public static void updatePenerbangan() throws IOException {
        File database = new File("src/database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        File tempDB = new File("src/tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        System.out.println("List Penerbangan");
        jadwalPenerbangan();

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor penerbangan yang akan diubah : ");
        int updateNum = terminalInput.nextInt();

        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null){
            entryCounts++;

            StringTokenizer st = new StringTokenizer(data,",");

            if (updateNum == entryCounts){
                System.out.println("Penerbangan yang ingin diubah adalah :");
                System.out.println("======================================");
                System.out.println("ID           : " + st.nextToken());
                System.out.println("Tanggal      : " + st.nextToken());
                System.out.println("Pesawat      : " + st.nextToken());
                System.out.println("Asal         : " + st.nextToken());
                System.out.println("Tujuan       : " + st.nextToken());
                System.out.println("Harga        : " + st.nextToken());
                System.out.println("Stok Tiket   : " + st.nextToken());

                String[] fieldData = {"tanggal","pesawat","asal","tujuan","harga","stok"};
                String[] tempData = new String[6];

                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();

                for (int i=0; i < fieldData.length; i++){
                    boolean isUpdate = YesOrNo("Apakah anda ingin mengubah " + fieldData[i] + " penerbangan ? [y/n]");

                    originalData = st.nextToken();
                    if (isUpdate){
                        terminalInput = new Scanner(System.in);
                        System.out.print("\nMasukan " + fieldData[i] + " baru : ");
                        tempData[i] = terminalInput.nextLine();
                    } else {
                        tempData[i] = originalData;
                    }

                }

                st = new StringTokenizer(data,",");
                st.nextToken();
                System.out.println("\nData baru anda adalah ");
                System.out.println("---------------------------------------");
                System.out.println("Tahun           : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Pesawat         : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Asal            : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("Tujuan          : " + st.nextToken() + " --> " + tempData[3]);
                System.out.println("Harga           : " + st.nextToken() + " --> " + tempData[4]);
                System.out.println("Stok Tiket      : " + st.nextToken() + " --> " + tempData[5]);

                boolean isUpdate = YesOrNo("Apakah anda yakin ingin mengupdate data penerbangan tersebut ? ");

                if (isUpdate){
                    boolean isExist = cekPenerbangan(tempData,false);

                    if(isExist){
                        System.err.println("data buku sudah ada di database, proses update dibatalkan, \nsilahkan delete data yang bersangkutan");

                        bufferedOutput.write(data);

                    } else {
                        long nomorEntry = ambilIdPenerbangan();

                        String tahun = tempData[0];
                        String pesawat = tempData[1];
                        String asal = tempData[2];
                        String tujuan = tempData[3];
                        String harga = tempData[4];
                        String stok = tempData[5];

                        bufferedOutput.write(nomorEntry + "," + tahun + ","+ pesawat + "," + asal + ","+tujuan+","+harga+","+stok);
                    }
                } else {
                    bufferedOutput.write(data);
                }

            } else {
                bufferedOutput.write(data);
            }
            bufferedOutput.newLine();

            data = bufferedInput.readLine();
        }

        bufferedOutput.flush();

        bufferedInput.close();
        bufferedOutput.close();

        database.delete();

        tempDB.renameTo(database);
    }

    public static long ambilIdPenerbangan() throws IOException {
        FileReader fileInput = new FileReader("src/database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();

        while(data != null){
            entry++;

            data = bufferInput.readLine();
        }

        bufferInput.close();

        return entry;
    }

    public static long ambilIdAkun() throws IOException {
        FileReader fileInput = new FileReader("src/user.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();

        while(data != null){
            entry++;

            data = bufferInput.readLine();
        }

        bufferInput.close();

        return entry;
    }

    public static void hapusPenerbangan() throws IOException {
        File database = new File("src/database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        File tempDB = new File("src/tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        System.out.println("Ini adalah data penerbangan didatabase");
        jadwalPenerbangan();

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor penerbangan yang akan dihapus : ");
        int deleteNum = terminalInput.nextInt();

        boolean isFound = false;

        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while(data != null){
            entryCounts++;
            Boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");

            if(deleteNum == entryCounts){
                System.out.println("Penerbangan yang ingin anda hapus : ");
                System.out.println("====================================");
                System.out.println("ID           : " + st.nextToken());
                System.out.println("Tanggal      : " + st.nextToken());
                System.out.println("Pesawat      : " + st.nextToken());
                System.out.println("Asal         : " + st.nextToken());
                System.out.println("Tujuan       : " + st.nextToken());
                System.out.println("Harga        : " + st.nextToken());

                isDelete = YesOrNo("Apakah anda yakin ingin menghapus penerbangan ini [y/n] : ");
                isFound = true;
            }

            if(isDelete){
                System.out.println("Penerbangan berhasil dihapus");
            } else {
                bufferedOutput.write(data);
                bufferedOutput.newLine();

            }
            data = bufferedInput.readLine();

        }

        if(!isFound){
            System.err.println("Penerbangan tidak ada didalam database");
        }

        bufferedOutput.flush();

        bufferedInput.close();
        bufferedOutput.close();

        database.delete();

        tempDB.renameTo(database);
    }

    public static void cariPenerbangan() throws IOException {
        try {
            File file = new File("src/database.txt");
        } catch (Exception e){
            System.err.println("Database tidak terhubung");
            System.err.println("Silahkan tambah data terlebih dahulu");
            return;
        }

        Scanner userOption = new Scanner(System.in);
        System.out.print("Masukan kata kunci tujuan untuk mencari jadwal penerbangan : ");
        String cariString = userOption.nextLine();
        System.out.println(cariString);

        String[] keywords = cariString.split("\\s+");

        cekPenerbangan(keywords, true);
    }

    public static boolean cekAkun(String[] keywords, boolean isDisplay) throws IOException {
        FileReader fileInput = new FileReader("src/user.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int jumlahData = 0;

        if(isDisplay){
            System.out.println("\n| ID | Username | Password |");
            System.out.println("==============================");
        }

        while (data != null){

            isExist = true;

            for(String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            if(isExist){
                if(isDisplay){
                    StringTokenizer stringToken = new StringTokenizer(data,",");

                    System.out.printf("| %4s ", stringToken.nextToken());
                    System.out.printf("| %4s ", stringToken.nextToken());
                    System.out.printf("| %-20s ", stringToken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }

        if(isDisplay){
            System.out.println("==============================");}

        bufferInput.close();

        return isExist;
    }

    public static boolean cekPenerbangan(String[] keywords, boolean isDisplay) throws IOException {
        FileReader fileInput = new FileReader("src/database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int jumlahData = 0;

        if(isDisplay) {
            System.out.println("\n|   ID | Tgl Brngkt | Maskapai             | Bandara              | Tujuan               | Harga      | Ketersediaan Tiket |");
            System.out.println("==============================================================================================================================");
        }

        while (data != null){

            isExist = true;

            for(String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            if(isExist){
                if(isDisplay){
                    StringTokenizer stringToken = new StringTokenizer(data,",");

                    System.out.printf("| %4s ", stringToken.nextToken());
                    System.out.printf("| %4s ", stringToken.nextToken());
                    System.out.printf("| %-20s ", stringToken.nextToken());
                    System.out.printf("| %-20s ", stringToken.nextToken());
                    System.out.printf("| %-20s ", stringToken.nextToken());
                    System.out.printf("| Rp.%s ", stringToken.nextToken());
                    System.out.printf("| %4s               |", stringToken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }

        if(isDisplay){
            System.out.println("==============================================================================================================================");
        }

        bufferInput.close();

        return isExist;
    }

    public static void dataPemesanan() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("src/pemesanan.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database tidak terhubung");
            return;
        }

        System.out.println("\n| Kode Booking | Tgl Brngkt | Maskapai             | Asal-Tujuan              | Penumpang                      | P | Harga      | Total Bayar |");
        System.out.println("=================================================================================================================================================");

        String data = bufferInput.readLine();

        while (data != null){
            StringTokenizer stringToken = new StringTokenizer(data,",");

            System.out.printf("| %-12s ", stringToken.nextToken());
            System.out.printf("| %-10s ", stringToken.nextToken());
            System.out.printf("| %-20s ", stringToken.nextToken());
            System.out.printf("| %-24s ", stringToken.nextToken());
            System.out.printf("| %-30s ", stringToken.nextToken() + "," + stringToken.nextToken() + "," + stringToken.nextToken() + "," + stringToken.nextToken());
            System.out.printf("| %s ", stringToken.nextToken());
            System.out.printf("| Rp.%s ", stringToken.nextToken());
            System.out.printf("| Rp.%-8s |", stringToken.nextToken());
            System.out.print("\n");
            data = bufferInput.readLine();
        }
        System.out.println("=================================================================================================================================================");
        bufferInput.close();
    }

    public static void jadwalPenerbangan() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("src/database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database tidak terhubung");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahPenerbangan();
            return;
        }

        System.out.println("\n|   ID | Tgl Brngkt | Maskapai             | Bandara              | Tujuan               | Harga      | Ketersediaan Tiket |");
        System.out.println("==============================================================================================================================");

        String data = bufferInput.readLine();

        while (data != null){
            StringTokenizer stringToken = new StringTokenizer(data,",");

            System.out.printf("| %4s ", stringToken.nextToken());
            System.out.printf("| %4s ", stringToken.nextToken());
            System.out.printf("| %-20s ", stringToken.nextToken());
            System.out.printf("| %-20s ", stringToken.nextToken());
            System.out.printf("| %-20s ", stringToken.nextToken());
            System.out.printf("| Rp.%s ", stringToken.nextToken());
            System.out.printf("| %4s               |", stringToken.nextToken());
            System.out.print("\n");
            data = bufferInput.readLine();
        }
        System.out.println("==============================================================================================================================");
        bufferInput.close();
    }

    public static void tambahPenerbangan() throws IOException {
        FileWriter fileOutput = new FileWriter("src/database.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        Scanner userOption = new Scanner(System.in);
        String tanggal,pesawat,asal,tujuan,harga,stok;

        System.out.print("Masukan Tanggal penerbangan : ");
        tanggal = userOption.nextLine();
        System.out.print("Masukan Pesawat penerbangan : ");
        pesawat = userOption.nextLine();
        System.out.println("Masukan Asal penerbangan : ");
        asal = userOption.nextLine();
        System.out.println("Masukan Tujuan penerbangan : ");
        tujuan = userOption.nextLine();
        System.out.println("Masukan Harga penerbangan : ");
        harga = userOption.nextLine();
        System.out.println("Masukan Stok penerbangan : ");
        stok = userOption.nextLine();

        String[] keywords = {tanggal+","+pesawat+","+asal+","+tujuan+","+harga};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = cekPenerbangan(keywords, false);
        System.out.println(isExist);

        if(!isExist){
            long nomorId = ambilIdPenerbangan() + 1;

            System.out.println("Data penerbangan yang anda masukan");

            System.out.println("ID : " + nomorId);
            System.out.println("Tanggal : " + tanggal);
            System.out.println("Pesawat : " + pesawat);
            System.out.println("Asal : " + asal);
            System.out.println("Tujuan : " + tujuan);
            System.out.println("Harga : " + harga);
            System.out.println("Harga : " + stok);

            boolean isTambah = YesOrNo("Apakah anda ingin menambahkan data tersebut? [y/n]");

            if(isTambah){
                bufferOutput.write(nomorId + "," + tanggal + "," + pesawat + "," + asal + "," + tujuan + "," + harga + "," + stok);
                bufferOutput.newLine();
                bufferOutput.flush();
                System.out.println("Berhasil menambahkan penerbangan ke dalam database");
            }

        } else {
            System.out.println("Data penerbangan yang anda masukan sudah ada");
            cekPenerbangan(keywords, true);
        }

        bufferOutput.close();
    }

    public static boolean YesOrNo(String msg){
        Scanner userOption = new Scanner(System.in);
        System.out.println(msg);
        String pilihanPengguna = userOption.next();

        while(!pilihanPengguna.equalsIgnoreCase("y") && !pilihanPengguna.equalsIgnoreCase("n")){
            System.err.println("Pilihan hanya y atau n");
            System.out.println(msg);
            pilihanPengguna = userOption.next();
        }

        return pilihanPengguna.equalsIgnoreCase("y");
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.println("\033\143");
            }
        } catch (Exception ex){
            System.out.println("Clear screen gagal!!!");
        }
    }
}
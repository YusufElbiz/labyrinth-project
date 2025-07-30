/**
 * @file Dosya FsmLabirentProje
 * @description Labirent Oyunu
 * @assignment 1.Proje
 * @date 30.12.2023
 * @author Yusuf Elbiz yusuf.elbiz@stu.fsm.edu.tr
 */
package fsmlabirentproje;

import java.util.Random;
import java.util.Scanner;

public class FsmLabirentProje {

    private static int AdimSayisi = 0;
    private static char[] Bonuslar;

    private static int Tsayaci = 0;
    private static int Rsayaci = 0;
    private static int Hsayaci = 0;
    private static int Fsayaci = 0;
    private static int KullanilmisTsayaci = 0;
    private static int KullanilmisHsayaci = 0;
    private static int KullanilmisRsayaci = 0;
    private static int KullanilmisFsayaci = 0;
    private static char[][] labirent
            = {{'#', '!', '.', '.', 'R', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'},
            {'.', '.', '#', '.', '.', '.', '#', '.', 'H', '.', '.', '.', '.', '.', '!'},
            {'F', '.', '.', '.', '#', '.', '!', '.', '.', 'R', '.', '.', '#', '#', '.'},
            {'.', '.', '#', '.', '.', '#', '.', '.', '.', '.', 'F', '.', '.', '.', '.'},
            {'.', '!', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '.', '.', '.'},
            {'.', '.', 'H', '.', '.', '!', '.', '.', 'H', '.', '.', 'F', '.', '.', 'R'},
            {'#', '#', '#', '#', '.', '.', '#', '.', '.', '.', 'T', '.', '.', '.', 'E'},
            {'.', '.', '#', '.', 'F', '.', '#', '#', '.', '#', '#', '#', '#', '.', '.'},
            {'.', '#', '.', '.', '.', '.', '!', '.', '#', '.', '.', '.', '#', '.', '.'},
            {'.', 'T', 'T', '.', '#', '#', '.', '.', '.', '.', 'T', '.', '.', '.', 'R'},
            {'.', '.', '.', '#', '.', '.', '.', '#', '.', '#', '.', '#', '.', 'T', '.'},
            {'B', '.', '#', '.', '.', '!', '.', '!', '.', '.', '.', '.', '.', '.', '#'},
            {'.', '.', '.', 'F', '!', '.', '.', '.', 'H', '.', '.', 'R', '.', '.', '.'},
            {'.', '.', 'H', '.', '.', '.', '!', '.', '.', '.', '#', '.', '.', '#', '.'},
            {'.', '.', '.', '#', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '#'}};

    public static void main(String[] args) {
        int[] anlikkonum = baslangickonumunubul(labirent);
        int[] oyunsonukonumu = bitiskonumunubul(labirent);
        konumuYazdir(labirent, anlikkonum);

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "W, A, S, D karakterlerinden birini giriniz ya da bonus kullanmak için + karakterine basınız. Çıkış için 'q' karakterine basınız.");
            char ilerle = input.next().charAt(0);
            if (ilerle == '+') {
                bonusKullan(input, Bonuslar, anlikkonum);
            } else {
                int[] yeniKonum = sonrakiKonum(anlikkonum, ilerle);
                String sonuc = ilerlemeyeUygun(yeniKonum, labirent);
                char mevcutDeger = labirent[anlikkonum[0]][anlikkonum[1]];
                if (mevcutDeger == 'T' || mevcutDeger == 'R' || mevcutDeger == 'F' || mevcutDeger == 'H') {

                    labirent[anlikkonum[0]][anlikkonum[1]] = '.';
                    bonusSayaci(mevcutDeger);
                }

                if (sonuc == "Doğru") {
                    anlikkonum = yeniKonum;
                    AdimSayisi++;
                    konumuYazdir(labirent, anlikkonum);
                } else {
                    System.out.println(sonuc);
                    konumuYazdir(labirent, anlikkonum);
                }
                if (AdimSayisi % 5 == 0) {
                    bonusKaldir(labirent);
                    mayinkaldir(labirent);
                    bonuslarinYeri(labirent);
                    mayinYeri(labirent);
                }
            }

            if (anlikkonum[0] == oyunsonukonumu[0] && anlikkonum[1] == oyunsonukonumu[1]) {
                System.out.println("Çıkışa ulaşıldı. Oyun tamamlandı.");
                break;
            }
            if (ilerle == 'q') {
                System.out.println("Çıkış yapılıyor....");
                break;
            }
            if (ilerle == 'Q') {
                System.out.println("Çıkış yapılıyor....");
                break;
            }
        }

    }

    private static int[] baslangickonumunubul(char[][] labirent) {
        int[] baslangıc​​Konumu = new int[2];
        for (int i = 0; i < labirent.length; i++) {
            if (labirent[i][0] == 'B') {
                baslangıc​​Konumu[0] = i;
                baslangıc​​Konumu[1] = 0;
                return baslangıc​​Konumu;
            }
        }

        return null;
    }

    public static boolean geçerliKonum(int x, int y, char[][] labirent) {

        if (x >= 0 && x < labirent.length && y >= 0 && y < labirent[0].length && labirent[x][y] != '#'
                && labirent[x][y] != '!') {
            return true;
        } else {
            return false;
        }
    }

    private static int[] bitiskonumunubul(char[][] labirent) {
        int[] oyunsonukonumu = new int[2];

        for (int i = 0; i < labirent.length; i++) {
            if (labirent[i][labirent[i].length - 1] == 'E') {
                oyunsonukonumu[0] = i;
                oyunsonukonumu[1] = labirent[i].length - 1;
                return oyunsonukonumu;
            }
        }

        return null;
    }

    private static void bonusSayaci(char bonus) {

        switch (bonus) {
            case 'T':
                Tsayaci++;
                break;
            case 'R':
                Rsayaci++;
                break;
            case 'H':
                Hsayaci++;
                break;
            case 'F':
                Fsayaci++;
                break;
        }

        System.out.println("t adeti: " + Tsayaci);
        System.out.println("r adeti: " + Rsayaci);
        System.out.println("h adeti: " + Hsayaci);
        System.out.println("f adeti: " + Fsayaci);
    }

    private static void bonuslarinYeri(char[][] labirent) {
        konumElemanlari(labirent, 'T', 5 - Tsayaci);
        konumElemanlari(labirent, 'R', 5 - Rsayaci);
        konumElemanlari(labirent, 'H', 5 - Hsayaci);
        konumElemanlari(labirent, 'F', 5 - Fsayaci);
    }

    private static void mayinYeri(char[][] labirent) {
        konumElemanlari(labirent, '!', 10);
    }

    public static void bonusKaldir(char[][] labirent) {
        for (int i = 0; i < labirent.length; i++) {
            for (int j = 0; j < labirent[i].length; j++) {
                if (labirent[i][j] == 'T' || labirent[i][j] == 'H' || labirent[i][j] == 'R' || labirent[i][j] == 'F') {
                    labirent[i][j] = '.';
                }
            }
        }
    }

    public static void mayinkaldir(char[][] labirent) {
        for (int i = 0; i < labirent.length; i++) {
            for (int j = 0; j < labirent[i].length; j++) {
                if (labirent[i][j] == '!') {
                    labirent[i][j] = '.';
                }
            }
        }
    }

    private static void konumuYazdir(char[][] labirent, int[] anlikkonum) {
        for (int i = 0; i < labirent.length; i++) {
            for (int j = 0; j < labirent[i].length; j++) {
                if (i == anlikkonum[0] && j == anlikkonum[1]) {
                    System.out.print("* "); // Üzerinde bulunulan pozisyonu "*" ile gösteriyor silinecek
                } else {
                    System.out.print(labirent[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Adım Sayısı: " + AdimSayisi);
        System.out.println("Bulunduğunuz konum: (" + anlikkonum[0] + ", " + anlikkonum[1] + ")");
    }

    private static int[] sonrakiKonum(int[] anlikkonum, char ilerle) {
        int row = anlikkonum[0];
        int col = anlikkonum[1];

        switch (ilerle) {
            case 'w', 'W':
                row--;
                break;
            case 'a', 'A':
                col--;
                break;
            case 's', 'S':
                row++;
                break;
            case 'd', 'D':
                col++;
                break;
        }

        int[] yeniKonum = new int[2];
        yeniKonum[0] = row;
        yeniKonum[1] = col;

        return yeniKonum;
    }

    private static String ilerlemeyeUygun(int[] pozisyon, char[][] labirent) {
        int row = pozisyon[0];
        int col = pozisyon[1];

        if (!(row >= 0 && row < labirent.length && col >= 0 && col < labirent[row].length)) {
            return "Sınırları geçemezsiniz.";
        } else if (labirent[row][col] == '#') {
            if ((Rsayaci - KullanilmisRsayaci) > 0) {
                labirent[row][col] = '.'; //HOCAYA SOR . İLE DEĞİŞMİYORSA KALDIRILACAK
                KullanilmisRsayaci++;
                System.out.println("bonus var kullanıldı."); //sil
                return "Doğru";
            } else {
                return "Duvar ile karşılaştınız.";
            }
        } else if (labirent[row][col] == '!') {
            if ((Fsayaci - KullanilmisFsayaci) > 0) {
                labirent[row][col] = '.';
                KullanilmisFsayaci++;
                System.out.println("bonus var kullanıldı.");//sil
                return "Doğru";
            } else {
                labirent[row][col] = '.';
                AdimSayisi = AdimSayisi + 5;
                return "Mayın ile karşılaşıldı.";
            }

        } else {
            return "Doğru";
        }

    }

    private static void konumElemanlari(char[][] labirent, char item, int sayac) {
        Random random = new Random();

        while (sayac > 0) {
            int row = random.nextInt(labirent.length);
            int col = random.nextInt(labirent[0].length);

            if (ElemanlariYerlestirebileceginYerler(row, col, labirent, item)) {
                labirent[row][col] = item;
                sayac--;
            }
        }
    }

    private static boolean ElemanlariYerlestirebileceginYerler(int row, int col, char[][] labirent, char oge) {
        char gecerliOge = labirent[row][col];
        return gecerliOge != '#' && gecerliOge != '!' && gecerliOge != 'T' && gecerliOge != 'R'
                && gecerliOge != 'H' && gecerliOge != 'F' && gecerliOge != 'E' && gecerliOge != 'B'
                && gecerliOge != oge;
    }

    private static void bonusKullan(Scanner input, char[] Bonuslar, int[] anlikkonum) {
        int kalanT = Tsayaci - KullanilmisTsayaci;
        int kalanH = Hsayaci - KullanilmisHsayaci;
        System.out.println(kalanT + " adet T bonusu var \n" + (Fsayaci - KullanilmisFsayaci) + " adet F bonusu var\n"
                + (Rsayaci - KullanilmisRsayaci) + " adet R bonusu var\n" + (kalanH
                + " adet H bonusu var\n") + "Kullanmak istediğin bonusu seç: ");
        char bonus = Character.toLowerCase(input.next().charAt(0));

        switch (bonus) {
            case 't':
                if (kalanT > 0) {
                    isinlan(input, anlikkonum);
                } else {
                    System.out.println("T Bonusu Yok.");
                }
                break;
            case 'r':
                break;
            case 'h':
                if (AdimSayisi >= 2) {
                    AdimSayisi = AdimSayisi - 2;
                    System.out.println("Adım sayınız 2 azaltıldı.\ns" + "Adım sayınız : " + AdimSayisi);
                    KullanilmisHsayaci++;

                } else {
                    System.out.println("Hamle sayınız zaten 2'den düşük.");

                }

                break;
            case 'f':

                break;
            default:
                System.out.println("Bonus Bulunmamaktadır.");
        }
    }

    private static void isinlan(Scanner input, int[] anlikkonum) {
        System.out.println("Işınlanmak istediğiniz konumu x girin: ");
        int newX = input.nextInt();
        System.out.println("Işınlanmak istediğiniz konumu y girin: ");
        int newY = input.nextInt();

        if (isinlanabilir(newX, newY, labirent)) {
            anlikkonum[0] = newX;
            anlikkonum[1] = newY;
            KullanilmisTsayaci++;
            konumuYazdir(labirent, anlikkonum);
        } else {
            System.out.println("Geçersiz ışınlanma konumu.Isınlanmak ıstedıgınız yerde duvar var. Yeni konumu tekrar girin.");
        }
    }

    private static boolean isinlanabilir(int x, int y, char[][] labirent) {
        return x >= 0 && x < labirent.length && y >= 0 && y < labirent[0].length && labirent[x][y] != '#'
                && labirent[x][y] != '!';
    }

}

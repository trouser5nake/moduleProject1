import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CaesarCipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};


    public static void encrypt(String path, int key) {

        char[] encryptedText = null;
        List<Character> sourceText = new ArrayList<>();

//        Читаем текст из файла
        if (Files.isRegularFile(Path.of(path))) {
            try (FileReader input = new FileReader(path);) {
                while (input.ready()) {
                    char ch = (char) (input.read());
                    sourceText.add(ch);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

//        Шифруем полученный текст
            encryptedText = new char[sourceText.size()];
            int index = 0;
            for (char ch : sourceText) {
                int i = -1;
                while (true) {
                    i++;
                    if (i == ALPHABET.length) {
                        break;
                    }
                    if (ch == ALPHABET[i]) {
                        encryptedText[index] = ALPHABET[(i + key) % 40];
                        index++;
                        break;
                    }
                }
            }
        }

//        Создаем новый файл и записываем в него зашифрованный текст
        Path sourceFile = Path.of(path);
        String inputFileName = sourceFile.getFileName().toString();
        String outputFileName = inputFileName.substring(0, inputFileName.length() - 4) + "encrypted.txt";
        String outputPath = sourceFile.getParent().toString() + "\\" + outputFileName;
        try (FileWriter output = new FileWriter(outputPath);) {
            output.write(encryptedText);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.printf("Готово, мой Повелитель! Зашифрованное послание хранится по адресу:\n\"%s\"\n\n", outputPath);
    }

    public static void decrypt(String path, int key) {

        char[] decryptedText = null;
        List<Character> sourceText = new ArrayList<>();

//        Читаем текст из файла
        if (Files.isRegularFile(Path.of(path))) {
            try (FileReader input = new FileReader(path);) {
                while (input.ready()) {
                    char ch = (char) (input.read());
                    sourceText.add(ch);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

//        Расшифровываем полученный текст
            decryptedText = new char[sourceText.size()];
            int index = 0;
            for (char ch : sourceText) {
                int i = -1;
                while (true) {
                    i++;
                    if (i == ALPHABET.length) {
                        System.err.println("Символа '" + ch + "' нет в алфавите");
                        break;
                    }
                    if (ch == ALPHABET[i]) {
                        decryptedText[index] = ALPHABET[(40 + i - key % 40) % 40];
                        index++;
                        break;
                    }
                }
            }
        }

//        Создаем новый файл и записываем в него расшифрованный текст
        Path sourceFile = Path.of(path);
        String inputFileName = sourceFile.getFileName().toString();
        String outputFileName = inputFileName.substring(0, inputFileName.length() - 4) + "decrypted.txt";
        String outputPath = sourceFile.getParent().toString() + "\\" + outputFileName;
        try (FileWriter output = new FileWriter(outputPath);) {
            output.write(decryptedText);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.printf("Готово, мой Повелитель! Расшифрованное послание хранится по адресу:\n\"%s\"\n\n", outputPath);
    }

    public static void main(String[] args) {
        encrypt("C:\\Distrib\\JAVA\\sourceee\\123.txt", 66);
        decrypt("C:\\Distrib\\JAVA\\sourceee\\123encrypted.txt", 66);
    }

}
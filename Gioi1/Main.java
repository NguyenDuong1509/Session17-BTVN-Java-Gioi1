package Ss17Rikke.Gioi1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookManager db = new BookManager();
        int choice = 0;

        while (choice != 6) {
            System.out.println("\n===== QUẢN LÝ THƯ VIỆN =====");
            System.out.println("1. Thêm sách");
            System.out.println("2. Cập nhật thông tin sách");
            System.out.println("3. Xóa sách");
            System.out.println("4. Tìm kiếm sách theo tác giả");
            System.out.println("5. Hiển thị tất cả sách");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng (1-6): ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Nhập tiêu đề sách: ");
                        String title = scanner.nextLine().trim();
                        System.out.print("Nhập tác giả: ");
                        String author = scanner.nextLine().trim();

                        if (title.isEmpty() || author.isEmpty()) {
                            throw new IllegalArgumentException("Lỗi: Tiêu đề và tác giả không được để trống!");
                        }

                        System.out.print("Nhập năm xuất bản: ");
                        int year = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Nhập giá sách: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());

                        Book newBook = new Book(title, author, year, price);
                        db.addBook(newBook);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Năm xuất bản phải là số nguyên, giá sách phải là số!");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Nhập ID sách cần cập nhật: ");
                        int updateId = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Nhập tiêu đề mới: ");
                        String newTitle = scanner.nextLine().trim();
                        System.out.print("Nhập tác giả mới: ");
                        String newAuthor = scanner.nextLine().trim();

                        if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                            throw new IllegalArgumentException("Lỗi: Tiêu đề và tác giả không được để trống!");
                        }

                        System.out.print("Nhập năm xuất bản mới: ");
                        int newYear = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Nhập giá sách mới: ");
                        double newPrice = Double.parseDouble(scanner.nextLine().trim());

                        Book updateBook = new Book(newTitle, newAuthor, newYear, newPrice);
                        db.updateBook(updateId, updateBook);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID, năm xuất bản và giá phải nhập đúng định dạng số!");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Nhập ID sách cần xóa: ");
                        int deleteId = Integer.parseInt(scanner.nextLine().trim());
                        db.deleteBook(deleteId);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: ID sách phải là một số!");
                    }
                    break;

                case 4:
                    System.out.print("Nhập tên tác giả cần tìm: ");
                    String searchAuthor = scanner.nextLine().trim();
                    if (searchAuthor.isEmpty()) {
                        System.out.println("Lỗi: Tên tác giả không được để trống!");
                    } else {
                        db.findBooksByAuthor(searchAuthor);
                    }
                    break;

                case 5:
                    db.listAllBooks();
                    break;

                case 6:
                    System.out.println("Chương trình kết thúc!");
                    break;

                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ!");
            }
        }
    }
}

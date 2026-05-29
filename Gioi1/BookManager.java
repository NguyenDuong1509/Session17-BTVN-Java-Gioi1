package Ss17Rikke.Gioi1;

import java.sql.*;

public class BookManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/librarydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "15092004";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private boolean isBookExistsById(int id) {
        String sql = "SELECT 1 FROM books WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean isDuplicateBook(String title, String author) {
        String sql = "SELECT 1 FROM books WHERE title = ? AND author = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public void addBook(Book book) {
        if (isDuplicateBook(book.getTitle(), book.getAuthor())) {
            System.out.println("Lỗi: Sách này đã tồn tại trong thư viện!");
            return;
        }

        String sql = "INSERT INTO books (title, author, published_year, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setDouble(4, book.getPrice());
            stmt.executeUpdate();
            System.out.println("Thêm sách thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void updateBook(int id, Book book) {
        if (!isBookExistsById(id)) {
            System.out.println("Lỗi: Không tìm thấy sách có ID = " + id);
            return;
        }

        String sql = "UPDATE books SET title = ?, author = ?, published_year = ?, price = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setDouble(4, book.getPrice());
            stmt.setInt(5, id);
            stmt.executeUpdate();
            System.out.println("Cập nhật sách thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        if (!isBookExistsById(id)) {
            System.out.println("Lỗi: Không tìm thấy sách có ID = " + id);
            return;
        }

        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Xóa sách thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void findBooksByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author ILIKE ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("Không tìm thấy sách của tác giả này!");
                    return;
                }

                System.out.printf("%-5s | %-30s | %-25s | %-10s | %-10s\n", "ID", "Tiêu đề", "Tác giả", "Năm XB", "Giá");
                System.out.println("-----------------------------------------------------------------------------------------");
                while (rs.next()) {
                    System.out.printf("%-5d | %-30s | %-25s | %-10d | %-10.2f\n",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("published_year"),
                            rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void listAllBooks() {
        String sql = "SELECT * FROM books ORDER BY id";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Thư viện hiện tại chưa có sách!");
                return;
            }

            System.out.printf("%-5s | %-30s | %-25s | %-10s | %-10s\n", "ID", "Tiêu đề", "Tác giả", "Năm XB", "Giá");
            System.out.println("-----------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-30s | %-25s | %-10d | %-10.2f\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("published_year"),
                        rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }
}

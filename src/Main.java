import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    private static JdbcImp dataSourceImp;
    private static Employee employee = new Employee();

    public static void main(String[] args) {
        dataSourceImp = new JdbcImp();
        int option;
        do {
            System.out.println("------------------- OPTIONS -------------------");
            System.out.println("1. INSERT AN EMPLOYEE");
            System.out.println("2. SELECT ALL EMPLOYEES");
            System.out.println("3. SELECT AN EMPLOYEE BY ID");
            System.out.println("4. SELECT AN EMPLOYEE BY NAME");
            System.out.println("5. UPDATE AN EMPLOYEE");
            System.out.println("6.DELETE AN EMPLOYEE");
            System.out.println("7. EXIT");
            System.out.println("------------------------------------------------");
            System.out.print("SELECT AN OPTION (1-7): ");
            option = input.nextInt();

            switch (option) {
                case 1 -> insertEmployee();
                case 2 -> selectAllRecords();
                case 3 -> selectRecordById();
                case 4 -> selectRecordByName();
                case 5 -> updateData();
                case 6 -> deleteData();
                case 7 -> System.out.println(">> Exiting program...");
                default -> System.out.println(">> Invalid option, please select again!");
            }
            input.nextLine();
            System.out.println(">> Press enter key to continue...");
            input.nextLine();
        } while (option != 7);

    }

    private static void displayRecord(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String gender = resultSet.getString("gender");
            Double salary = resultSet.getDouble("salary");
            employeeList.add(new Employee(id, name, gender, salary));
        }
        System.out.println(employeeList);
    }

    private static void selectAllRecords() {
        System.out.println("3. SELECT ALL EMPLOYEES");
        try (Connection connection = dataSourceImp.dataSource().getConnection()) {
            String selectSql = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(selectSql);
            displayRecord(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void selectRecordById() {
        System.out.println("3. SELECT AN EMPLOYEE BY ID");
        try (Connection connection = dataSourceImp.dataSource().getConnection()) {
            String selectSql = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(selectSql);
            System.out.print(">> Enter employee's ID you want to select : ");
            int selectId = input.nextInt();
            statement.setInt(1, selectId);
            displayRecord(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectRecordByName() {
        System.out.println("4. SELECT AN EMPLOYEE BY NAME");
        System.out.println();
        try (Connection connection = dataSourceImp.dataSource().getConnection()) {
            String selectSql = "SELECT * FROM employees WHERE name = ?";

            PreparedStatement statement = connection.prepareStatement(selectSql);
            System.out.print(">> Enter employee's name you want to select : ");
            String selectName = input.next();
            statement.setString(1, selectName);
            displayRecord(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertEmployee() {
        System.out.println("1. INSERT AN EMPLOYEE");
        System.out.println();
        input.nextLine();
        System.out.print("Enter name : ");
        employee.setName(input.nextLine());
        System.out.print("Enter gender : ");
        employee.setGender(input.nextLine());
        System.out.print("Enter salary : ");
        employee.setSalary(input.nextDouble());

        try (Connection conn = dataSourceImp.dataSource().getConnection()) {
            String insertSql = "INSERT INTO employees ( name, gender, salary) VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(insertSql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getGender());
            statement.setDouble(3, employee.getSalary());
            statement.executeUpdate();
            System.out.println(">> An employee is successfully inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateData() {
        System.out.println("5. UPDATE AN EMPLOYEE");
        System.out.println();
        System.out.print(">> Enter employee's id you want to update : ");
        int updateId = input.nextInt();
        input.nextLine();
        System.out.print("Enter new name : ");
        employee.setName(input.nextLine());
        System.out.print("Enter new gender : ");
        employee.setGender(input.nextLine());
        System.out.println("Enter new salary : ");
        employee.setSalary(input.nextDouble());

        try (Connection conn = dataSourceImp.dataSource().getConnection()) {
            String updateSql = "UPDATE employees SET name = ?, gender = ?, salary = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(updateSql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getGender());
            statement.setDouble(3, employee.getSalary());
            statement.setInt(4, updateId);
            statement.executeUpdate();
            System.out.println(">> An employee with id " + updateId + " is successfully updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void deleteData() {
        System.out.println("6. DELETE AN EMPLOYEE");
        System.out.println();
        try (Connection conn = dataSourceImp.dataSource().getConnection()) {
            String updateSql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(updateSql);
            System.out.print(">> Enter employee's id you want to delete : ");
            int deleteId = input.nextInt();
            statement.setInt(1, deleteId);
            statement.executeUpdate();
            System.out.println(">> An employee with id " + deleteId + " is successfully updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

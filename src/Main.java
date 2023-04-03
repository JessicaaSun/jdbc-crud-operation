import model.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    private static JdbcImp dataSourceImp;
    private static Employee employee = new Employee();

    public static void main(String[] args) {
        dataSourceImp  = new JdbcImp();
        displayData();
        insertEmployee();
        displayData();
    }

    private static void displayData(){
        try(Connection connection = dataSourceImp.dataSource().getConnection()) {
            String selectSql = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(selectSql);
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

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void insertEmployee(){
        System.out.print("Enter name : ");
        employee.setName(input.nextLine());
        System.out.print("Enter gender : ");
        employee.setGender(input.nextLine());
        System.out.println("Enter salary : ");
        employee.setSalary(input.nextDouble());

        try (Connection conn = dataSourceImp.dataSource().getConnection()) {
            String insertSql = "INSERT INTO employees ( name, gender, salary) VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(insertSql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getGender());
            statement.setDouble(3, employee.getSalary());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }



}

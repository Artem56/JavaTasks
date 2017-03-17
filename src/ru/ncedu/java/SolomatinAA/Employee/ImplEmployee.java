package ru.ncedu.java.SolomatinAA.Employee;
/**
 * Created by Artem Solomatin on 15.03.17.
 * Employee
 */
public class ImplEmployee implements Employee {
    private int salary = 1000;
    private String name;
    private String surname;
    private Employee manager;

    public ImplEmployee() {
        name = "NAME";
        surname = "SURNAME";
    }

    public ImplEmployee(String name, String surname) {   //TO REMOVE
        this.name = name;
        this.surname = surname;
    }

    public ImplEmployee(String name, String surname, Employee manager) {   //TO REMOVE
        this.name = name;
        this.surname = surname;
        this.manager = manager;
    }

    /**
     * @return Зарплата сотрудника на настоящий момент.
     */
    @Override
    public int getSalary() {
        return salary;
    }

    /**
     * Увеличивает зарплату сотрудника на заданное значение
     *
     * @param value Значение, на которое нужно увеличить
     */
    @Override
    public void increaseSalary(int value) {
        salary+=value;
    }    //нужно ли проверять чтоб зарплата не ушла в минус?

    /**
     * @return Имя сотрудника
     */
    @Override
    public String getFirstName() {
        return name;
    }

    /**
     * Устанавливает имя сотрудника
     *
     * @param firstName Новое имя
     */
    @Override
    public void setFirstName(String firstName) {
        name = firstName;
    }

    /**
     * @return Фамилия сотрудника
     */
    @Override
    public String getLastName() {
        return surname;
    }

    /**
     * Устанавливает фамилию сотрудника
     *
     * @param lastName Новая фамилия
     */
    @Override
    public void setLastName(String lastName) {
        surname = lastName;
    }

    /**
     * @return Имя и фамилия сотрудника, разделенные символом " " (пробел)
     */
    @Override
    public String getFullName() {
        return name + " " + surname;
    }

    /**
     * Устанавливает Менеджера сотрудника.
     *
     * @param manager Сотрудник, являющийся менеджером данного сотрудника.
     *                НЕ следует предполагать, что менеджер является экземпляром класса EmployeeImpl.
     */
    @Override
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * @return Имя и фамилия Менеджера, разделенные символом " " (пробел).
     * Если Менеджер не задан, возвращает строку "No manager".
     */
    @Override
    public String getManagerName() {
        if(manager != null ){
            return manager.getFullName();
        }
        return "No manager";
    }

    /**
     * Возвращает Менеджера верхнего уровня, т.е. вершину иерархии сотрудников,
     * в которую входит данный сотрудник.
     * Если над данным сотрудником нет ни одного менеджера, возвращает данного сотрудника.
     * Замечание: поскольку менеджер, установленный методом {@link #setManager(Employee)},
     * может быть экзепляром другого класса, при поиске топ-менеджера нельзя обращаться
     * к полям класса EmployeeImpl. Более того, поскольку в интерфейсе Employee не объявлено
     * метода getManager(), поиск топ-менеджера невозможно организовать в виде цикла.
     * Вместо этого нужно использовать рекурсию (и это "более объектно-ориентированно").
     */
    @Override
    public Employee getTopManager() {
        if( manager == null){
            return this;

        }
        return manager.getTopManager();
    }
}

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> events = new ArrayList<>();
        while ( n-- > 0 && scanner.hasNextLine()){
            events.add(scanner.nextLine());
        }
        Priorities priorities = new Priorities();
        List<Student> missingStudents = priorities.getStudents(events);
        if(missingStudents.isEmpty()){
            System.out.println("EMPTY");
        }else{
            for (Student student: missingStudents){
                System.out.println(student.getName());
            }
        }
        scanner.close();
    }
    private static class Student{
        private int id;
        private String name;
        private double cgpa;
        public Student(int id, String name, double cgpa){
            this.id = id;
            this.name = name;
            this.cgpa = cgpa;
        }

        public int getId() {
            return id;
        }

        public double getCgpa() {
            return cgpa;
        }

        public String getName() {
            return name;
        }
    }

    private static class Priorities {

        private List<Student> studentList;

        List<Student> getStudents(List<String> events) {
            List<Student> result = new ArrayList<>();
            Comparator<Student> comparator = Comparator.comparingDouble(Student::getCgpa).reversed().thenComparing(Student::getName).thenComparingInt(Student::getId);
            PriorityQueue<Student> queuePriorityStudent = new PriorityQueue<>(comparator);
            for (String event : events) {
                String[] cadena = event.split(" ");
                String typeEvent = cadena[0];
                if (typeEvent.equals("ENTER")){
                    double cgpa = Double.parseDouble(cadena[2]);
                    int ip = Integer.parseInt(cadena[3]);
                    String name = cadena[1];

                    if((cgpa <= 4.00 && cgpa >= 0 ) && (ip <= Math.pow(10, 5) && ip >= 1 ) && (name.length() <= 30 && name.length() >= 2)){
                        queuePriorityStudent.offer(new Student(ip,name,cgpa));
                    }else{
                        continue;
                    }
                } else if (typeEvent.equals("SERVED")) {
                    if(!queuePriorityStudent.isEmpty()){
                        queuePriorityStudent.poll();
                    }else{
                        continue;
                    }
                } else{
                    continue;
                }
            }
            while (!queuePriorityStudent.isEmpty()){
                result.add(queuePriorityStudent.poll());
            }
            return result;
        }
    }
}
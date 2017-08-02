package com.kaishengit.hibernate;

import java.util.HashSet;
import java.util.Set;

import com.kaishengit.pojo.Student;
import com.kaishengit.pojo.Teacher;

public class ManyToMany extends BaseTest{
	
	 /**
	 * ��������ѧ��s1 s2 ����������ʦt1 t2
	 */
	@org.junit.Test
	    public void save() {
	        
	        Student s1 = new Student();
	        s1.setName("s1");

	        Student s2 = new Student();
	        s2.setName("s2");

	        Teacher t1 = new Teacher();
	        t1.setName("t1");

	        Teacher t2 = new Teacher();
	        t2.setName("t2");


	        Set<Teacher> teacherSet = new HashSet<Teacher>();
	        teacherSet.add(t1);
	        teacherSet.add(t2);

	        s1.setTeacherSet(teacherSet);
	        s2.setTeacherSet(teacherSet);

	       //Student�����ϵ,��ǰ����¿��Բ�д
	        Set<Student> studentSet = new HashSet<Student>();
	        studentSet.add(s1);
	        studentSet.add(s2);
	        t1.setStudentSet(studentSet);
	        t2.setStudentSet(studentSet);

	        session.save(t1);
	        session.save(t2);

	        session.save(s1);
	        session.save(s2);
	    }

	    /**
	     * ͨ��ѧ��ID����ʦ
	     */
	    @org.junit.Test
	    public void findByStudentId() {

	        Student student = (Student) session.get(Student.class,3);
	        System.out.println(student.getName());

	        //�ӳټ���
	        Set<Teacher> teacherSet = student.getTeacherSet();
	        for(Teacher teacher : teacherSet) {
	            System.out.println(teacher.getId() + " -> " + teacher.getName());
	        }
	    }

}
	

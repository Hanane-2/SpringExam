package employe.management.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    
     //enregistrer chaque mise à jour
    

    @Before("execution(* employe.management.Service.EmployeService.addEmployee(..))")
    public void logBeforeAddEmployee(JoinPoint joinPoint) {
        logger.info("Adding new employee: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* employe.management.Service.EmployeService.addEmployee(..))")
    public void logAfterAddEmployee(JoinPoint joinPoint) {
        logger.info("Employee added successfully");
    }

    @Before("execution(* employe.management.Service.EmployeService.updateEmployee(..))")
    public void logBeforeUpdateEmployee(JoinPoint joinPoint) {
        logger.info("Updating employee: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* employe.management.Service.EmployeService.updateEmployee(..))")
    public void logAfterUpdateEmployee(JoinPoint joinPoint) {
        logger.info("Employee updated successfully");
    }

    @Before("execution(* employe.management.Service.EmployeService.deleteEmployeeById(..))")
    public void logBeforeDeleteEmployee(JoinPoint joinPoint) {
        logger.info("Deleting employee with ID: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* employe.management.Service.EmployeService.deleteEmployeeById(..))")
    public void logAfterDeleteEmployee(JoinPoint joinPoint) {
        logger.info("Employee deleted successfully");
    }
}
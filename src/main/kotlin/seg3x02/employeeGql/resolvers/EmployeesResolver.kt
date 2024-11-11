package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee =
                Employee(
                        name = createEmployeeInput.name!!,
                        dateOfBirth = createEmployeeInput.dateOfBirth!!,
                        city = createEmployeeInput.city!!,
                        salary = createEmployeeInput.salary!!,
                        gender = createEmployeeInput.gender,
                        email = createEmployeeInput.email
                )
        return employeesRepository.save(employee)
    }
}

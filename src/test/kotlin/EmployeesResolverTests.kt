package seg3x02.employeeGql.resolvers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@ExtendWith(MockitoExtension::class)
class EmployeesResolverTests {

    @Mock private lateinit var employeesRepository: EmployeesRepository

    @InjectMocks private lateinit var employeesResolver: EmployeesResolver

    @Test
    fun `should return all employees`() {
        // Given
        val employees =
                listOf(
                        Employee(
                                "John Doe",
                                "1990-01-01",
                                "New York",
                                50000f,
                                "M",
                                "john@example.com"
                        ),
                        Employee(
                                "Jane Doe",
                                "1992-02-02",
                                "Boston",
                                60000f,
                                "F",
                                "jane@example.com"
                        )
                )
        `when`(employeesRepository.findAll()).thenReturn(employees)

        // When
        val result = employeesResolver.employees()

        // Then
        assertThat(result).hasSize(2)
        assertThat(result).isEqualTo(employees)
    }

    @Test
    fun `should create new employee`() {
        // Given
        val input =
                CreateEmployeeInput(
                        name = "John Doe",
                        dateOfBirth = "1990-01-01",
                        city = "New York",
                        salary = 50000f,
                        gender = "M",
                        email = "john@example.com"
                )

        val expectedEmployee =
                Employee(
                        name = input.name!!,
                        dateOfBirth = input.dateOfBirth!!,
                        city = input.city!!,
                        salary = input.salary!!,
                        gender = input.gender,
                        email = input.email
                )

        `when`(employeesRepository.save(org.mockito.kotlin.any())).thenReturn(expectedEmployee)

        // When
        val result = employeesResolver.newEmployee(input)

        // Then
        assertThat(result.name).isEqualTo(input.name)
        assertThat(result.dateOfBirth).isEqualTo(input.dateOfBirth)
        assertThat(result.city).isEqualTo(input.city)
        assertThat(result.salary).isEqualTo(input.salary)
        assertThat(result.gender).isEqualTo(input.gender)
        assertThat(result.email).isEqualTo(input.email)
    }
}

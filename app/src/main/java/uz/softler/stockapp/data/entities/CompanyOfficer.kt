package uz.softler.stockapp.data.entities

data class CompanyOfficer(
    val age: Int,
    val exercisedValue: ExercisedValue,
    val fiscalYear: Int,
    val maxAge: Int,
    val name: String,
    val title: String,
    val totalPay: TotalPay,
    val unexercisedValue: UnexercisedValue,
    val yearBorn: Int
)
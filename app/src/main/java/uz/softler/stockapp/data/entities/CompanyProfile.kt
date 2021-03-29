package uz.softler.stockapp.data.entities

data class CompanyProfile(
    val address1: String,
    val auditRisk: Int,
    val boardRisk: Int,
    val city: String,
    val companyOfficers: List<CompanyOfficer>,
    val compensationAsOfEpochDate: Int,
    val compensationRisk: Int,
    val country: String?,
    val fullTimeEmployees: Int,
    val governanceEpochDate: Int,
    val industry: String,
    val longBusinessSummary: String,
    val maxAge: Int,
    val overallRisk: Int,
    val phone: String,
    val sector: String,
    val shareHolderRightsRisk: Int,
    val state: String,
    val website: String,
    val zip: String
)
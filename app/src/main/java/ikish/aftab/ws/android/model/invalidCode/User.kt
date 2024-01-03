package ikish.aftab.ws.android.model.invalidCode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("mobile")
    @Expose
    private var mobile: String? = null,

    @SerializedName("username")
    @Expose
    private var username: Any? = null,

    @SerializedName("fname")
    @Expose
    private var fname: Any? = null,

    @SerializedName("lname")
    @Expose
    private var lname: Any? = null,

    @SerializedName("birth_date")
    @Expose
    private var birthDate: Any? = null,

    @SerializedName("national_code")
    @Expose
    private var nationalCode: Any? = null,

    @SerializedName("gender")
    @Expose
    private var gender: Int? = null,

    @SerializedName("email")
    @Expose
    private var email: Any? = null,

    @SerializedName("status")
    @Expose
    private var status: Int? = null,

    @SerializedName("job")
    @Expose
    private var job: Any? = null,

    @SerializedName("education_level")
    @Expose
    private var educationLevel: Int? = null,

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
) {


    fun getMobile(): String? {
        return mobile
    }

    fun setMobile(mobile: String?) {
        this.mobile = mobile
    }

    fun getUsername(): Any? {
        return username
    }

    fun setUsername(username: Any?) {
        this.username = username
    }

    fun getFname(): Any? {
        return fname
    }

    fun setFname(fname: Any?) {
        this.fname = fname
    }

    fun getLname(): Any? {
        return lname
    }

    fun setLname(lname: Any?) {
        this.lname = lname
    }

    fun getBirthDate(): Any? {
        return birthDate
    }

    fun setBirthDate(birthDate: Any?) {
        this.birthDate = birthDate
    }

    fun getNationalCode(): Any? {
        return nationalCode
    }

    fun setNationalCode(nationalCode: Any?) {
        this.nationalCode = nationalCode
    }

    fun getGender(): Int? {
        return gender
    }

    fun setGender(gender: Int?) {
        this.gender = gender
    }

    fun getEmail(): Any? {
        return email
    }

    fun setEmail(email: Any?) {
        this.email = email
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getJob(): Any? {
        return job
    }

    fun setJob(job: Any?) {
        this.job = job
    }

    fun getEducationLevel(): Int? {
        return educationLevel
    }

    fun setEducationLevel(educationLevel: Int?) {
        this.educationLevel = educationLevel
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }
}
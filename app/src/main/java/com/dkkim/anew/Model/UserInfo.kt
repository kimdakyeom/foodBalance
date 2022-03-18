package com.dkkim.anew.Model

class UserInfo(
    _name: String?,
    _sex: Boolean?,
    _phone: String?,
    _birth: String?,
    _height: String?,
    _weight: String?
) {
    var name: String? = _name
        get() = field
        set(value) {
            field = value
        }
    var sex: Boolean? = _sex
        get() = field
        set(value) {
            field = value
        }
    var phone: String? = _phone
        get() = field
        set(value) {
            field = value
        }
    var birth: String? = _birth
        get() = field
        set(value) {
            field = value
        }
    var height: String? = _height
        get() = field
        set(value) {
            field = value
        }
    var weight: String? = _weight
        get() = field
        set(value) {
            field = value
        }


}
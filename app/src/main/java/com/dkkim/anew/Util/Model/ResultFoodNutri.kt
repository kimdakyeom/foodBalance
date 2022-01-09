package com.dkkim.anew.Model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "response", strict = false)
data class ResultFoodNutri(
    @Element(name = "header") val header: Header,
    @Element(name="body") val body: Body
)

@Root(name = "header")
data class Header(
    @Element(name = "result_Code") var resultCode: Int,
    @Element(name = "result_Msg") var resultMsg: String
)

@Root(name = "body")
data class Body(
    @Element(name="items") var items: List<FoodNutriInfo>
)

@Root(name = "item")
data class FoodNutriInfo(
    @Element(name = "main_Food_Code") var foodCode: String,
    @Element(name = "main_Food_Name") var foodName: String,
    @ElementList(inline = true) var idntList: List<IdntList>
)

@Root(name = "idnt_List")
data class IdntList(
    @Element(name = "food_Code") var foodCode: String,
    @Element(name = "food_Name") var foodName: String,
    @Element(name = "food_Weight") var foodWeight: Int,
    @Element(name = "energy_Qy") var energy: Double,
    @Element(name = "water_Qy") var water: Double,
    @Element(name = "prot_Qy") var prot: Double,
    @Element(name = "ntrfs_Qy") var ntrfs: Double,
    @Element(name = "ashs_Qy") var ashs: Double,
    @Element(name = "carbohydrate_Qy") var carbohydrate: Double,
    @Element(name = "sugar_Qy") var sugar: Double,
    @Element(name = "fibtg_Qy") var fibtg: Double,
    @Element(name = "aat19_Qy") var aat19: Double,
    @Element(name = "aae10a_Qy") var aae10a: Double,
    @Element(name = "aane_Qy") var aane: Double,
    @Element(name = "fafref_Qy") var fafref: Double,
    @Element(name = "faessf_Qy") var faessf: Double,
    @Element(name = "fasatf_Qy") var fasatf: Double,
    @Element(name = "famsf_Qy") var famsf: Double,
    @Element(name = "fapuf_Qy") var fapuf: Double,
    @Element(name = "clci_Qy") var clci: Double,
    @Element(name = "irn_Qy") var irn: Double,
    @Element(name = "mg_Qy") var mg: Double,
    @Element(name = "phph_Qy") var phph: Double,
    @Element(name = "ptss_Qy") var ptss: Double,
    @Element(name = "na_Qy") var na: Double,
    @Element(name = "zn_Qy") var zn: Double,
    @Element(name = "cu_Qy") var cu: Double,
    @Element(name = "mn_Qy") var mn: Double,
    @Element(name = "se_Qy") var se: Double,
    @Element(name = "mo_Qy") var mo: Double,
    @Element(name = "id_Qy") var id: Double,
    @Element(name = "rtnl_Qy") var rtnl: Double,
    @Element(name = "catn_Qy") var catn: Double,
    @Element(name = "vitd_Qy") var vitd: Double,
    @Element(name = "vitk1_Qy") var vitk1: Double,
    @Element(name = "vtmn_B1_Qy") var vtmn_B1: Double,
    @Element(name = "vtmn_B2_Qy") var vtmn_B2: Double,
    @Element(name = "nacn_Qy") var nacn: Double,
    @Element(name = "pantac_Qy") var pantac: Double,
    @Element(name = "pyrxn_Qy") var pyrxn: Double,
    @Element(name = "biot_Qy") var biot: Double,
    @Element(name = "fol_Qy") var fol: Double,
    @Element(name = "vitb12_Qy") var vitb12: Double,
    @Element(name = "vtmn_C_Qy") var vtmn_C: Double,
    @Element(name = "chole_Qy") var chole: Double,
    @Element(name = "nacl_Qy") var nacl: Double,
    @Element(name = "ref_Qy") var ref: Double

)


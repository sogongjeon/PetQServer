package com.sogong.sogong.controller.animal

//import com.sogong.sogong.entity.animal.AnimalKindDetailDto
//import com.sogong.sogong.entity.animal.AnimalKindDetailDto

//import org.apache.tomcat.util.http.fileupload.IOUtils
//import jdk.jpackage.internal.IOUtils
//import org.apache.commons.io.IOUtils.toByteArray

import com.sogong.sogong.converter.animal.MainAnimalListConverter
import com.sogong.sogong.entity.EntityList
import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.animal.AnimalDataEntity
import com.sogong.sogong.entity.animal.AnimalKindDetailDto
import com.sogong.sogong.entity.animal.AnimalSearchCriteria
import com.sogong.sogong.entity.animal.RegisterAnimalRequest
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.services.animal.AnimalDataService
import com.sogong.sogong.services.animal.AnimalKindService
import com.sogong.sogong.services.district.GuService
import com.sogong.sogong.type.animal.AnimalKindType
import com.sogong.sogong.type.animal.ProtectType
import org.apache.commons.io.IOUtils.toByteArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.servlet.ServletContext


@RestController
@RequestMapping("/v1/animal")
class AnimalController(
        private val animalDataService: AnimalDataService,
        private val restTemplate: RestTemplate,
        private val animalKindService: AnimalKindService,
        private val guService : GuService,
        private val servletContext : ServletContext
) {
    private val log: Logger = LoggerFactory.getLogger(AnimalController::class.java)

    @Value("\${animalImage.savePath}")
    private val savePath = ""

    @Value("\${animalImage.getImagePath}")
    private val imagePath = ""


    @GetMapping("/list")
    fun animalList(@ModelAttribute criteria : AnimalSearchCriteria) : ResultEntity<EntityList<AnimalDataEntity>> {

        var animalPage = animalDataService.listByAnimalCriteria(criteria)

        var converter = MainAnimalListConverter(imagePath, animalKindService, guService)

        val animalResult = EntityList<AnimalDataEntity>()
        animalResult.totalCount = animalPage!!.totalElements
        animalResult.elements = animalPage.stream()
                .map(converter::convert)
                .toList()

        return ResultEntity(animalResult)
    }

    @GetMapping("/kind")
    fun getAnimalKind(@RequestParam(value="type", required=false) type : String?) : ResultEntity<EntityList<AnimalKindDetailDto>> {
        var animalKindList = animalKindService.findByType(type)

        var animalKindDto : ArrayList<AnimalKindDetailDto> = ArrayList()
        val result = EntityList<AnimalKindDetailDto>()

        if(animalKindList != null) {
            for(animalKind in animalKindList) {
                var animalKindDetail = AnimalKindDetailDto()
                animalKindDetail.kind = AnimalKindType.valueOf(animalKind.type).value
                animalKindDetail.kindDetailName = animalKind.kindName
                animalKindDetail.kindDetailCode = animalKind.kindCode

                animalKindDto.add(animalKindDetail)
            }
            result.elements = animalKindDto
            result.totalCount = animalKindDto.size.toLong()

        }
        return ResultEntity(result)
    }

    @PostMapping("/register")
    fun findAnimalRegister(@ModelAttribute request : RegisterAnimalRequest) : ResultEntity<Boolean> {
        var animalKind = animalKindService.findByTypeCode(request.animalKindCode!!)

        var newAnimalData = AnimalData()

        newAnimalData.protectType = ProtectType.valueOf(request.protectType!!)
        newAnimalData.guCode = request.guCode
        newAnimalData.animalKindCode = request.animalKindCode
        newAnimalData.happenedDate = LocalDate.parse(request.happenedDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0, 0, 0);
        newAnimalData.happenedPlace = request.happenedPlace
        newAnimalData.kindName = "[${AnimalKindType.valueOf(animalKind!!.type).value}] ${animalKind!!.kindName}"
        newAnimalData.color = request.color
        newAnimalData.birthYear = request.birthYear!!.toInt()
        newAnimalData.weight = request.weight
        newAnimalData.processState = "보호중"
        newAnimalData.sex = request.sex
        newAnimalData.isNeutered = request.isNeutered
        newAnimalData.specialMark = request.specialMark
        newAnimalData.managerName = request.managerName
        newAnimalData.managerTel = request.managerTel

        animalDataService.saveOrUpdate(newAnimalData)

        println("아이디${newAnimalData.id}")

        //이미지 집어넣긔
        if(request.image == null) {
            return ResultEntity("1500", "이미지가 없을수가 없다.")
        }

        try {
            val dest = File(savePath + newAnimalData.id + ".jpg")

            request.image!!.transferTo(dest)

            println("${dest}로 이미지 저장완료!")
        } catch(e : IllegalStateException ) {
            e.printStackTrace();
        } catch (e : IOException) {
            e.printStackTrace();
        }

        return ResultEntity(true)
    }


    //    @Autowired
    //    ServletContext servletContext;
//    @GetMapping(value = ["/image"], produces = [MediaType.IMAGE_JPEG_VALUE])
//    @ResponseBody
//    @Throws(IOException::class)
//    fun getImageWithMediaType(): ByteArray? {
//        println("savePath : $savePath")
//        val a = savePath + "4562.jpg"
//        val `in` = servletContext.getResourceAsStream(a)
//        return toByteArray(`in`)
//    }


}

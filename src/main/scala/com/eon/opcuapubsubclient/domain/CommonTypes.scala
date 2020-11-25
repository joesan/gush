package com.eon.opcuapubsubclient.domain

import java.util.UUID
import com.eon.opcuapubsubclient.domain.HeaderTypes.{DataValue, ExtensionObject}
import play.api.libs.json._
import julienrf.json.derived


object CommonTypes {

  sealed trait VariantData
  case class SimpleOrder(rows: Vector[BuiltInType]) extends VariantData
  case class HigherOrder(matrices: Vector[VariantData]) extends VariantData
  object VariantData {
    implicit val jsonFormat: OFormat[VariantData] = derived.oformat[VariantData]()
  }

  case class Variant(data: VariantData)
  object Variant {
    implicit val jsonFormat: OFormat[Variant] = derived.oformat[Variant]()
  }

  case class StatusCode(value: Long) {
    private val severityMask = 0xC0000000L
    private val severityGood = 0x00000000L
    private val severityUncertain = 0x40000000L
    private val severityBad = 0x80000000L

    def isStatusGood: Boolean = (value & severityMask) == severityGood
    def isStatusBad: Boolean = (value & severityMask) == severityBad
    def isStatusUncertain: Boolean = (value & severityMask) == severityUncertain
  }
  object StatusCode {
    implicit val jsonFormat: OFormat[StatusCode] = derived.oformat[StatusCode]()
  }

  // ******************************************* BuiltInTypes  ****************************************************** //

  sealed trait BuiltInType { def id: Int }
  case class ZombieType          (a: String,        id: Int = 0) extends BuiltInType
  case class BooleanType         (a: Boolean,       id: Int = 1) extends BuiltInType
  case class ByteType            (a: Byte,          id: Int = 2) extends BuiltInType
  case class UByteType           (a: Byte,          id: Int = 3) extends BuiltInType
  case class Int16Type           (a: Int,           id: Int = 4) extends BuiltInType
  case class UInt16Type          (a: Int,           id: Int = 5) extends BuiltInType
  case class Int32Type           (a: Int,           id: Int = 6) extends BuiltInType
  case class UInt32Type          (a: Int,           id: Int = 7) extends BuiltInType
  case class Int64Type           (a: Long,          id: Int = 8) extends BuiltInType
  case class UInt64Type          (a: Long,          id: Int = 9) extends BuiltInType
  case class FloatType           (a: Float,         id: Int = 10) extends BuiltInType
  case class DoubleType          (a: Double,        id: Int = 11) extends BuiltInType
  case class StringType          (a: String,        id: Int = 12) extends BuiltInType
  case class DateTimeType        (a: Long,          id: Int = 13) extends BuiltInType // FIXME: Wrong type used, fix it later
  case class GuidType            (a: UUID,          id: Int = 14) extends BuiltInType
  case class ByteStringType      (a: Vector[Byte],  id: Int = 15) extends BuiltInType
  case class XmlElementType      (a: String,        id: Int = 16) extends BuiltInType
  case class NodeIdType          (a: NodeId,        id: Int = 17) extends BuiltInType
  case class ExpandedNodeIdType  (a: NodeId,        id: Int = 18) extends BuiltInType // FIXME: Wrong type used, fix it later
  case class StatusCodeType      (a: StatusCode,    id: Int = 19) extends BuiltInType
  case class QualifiedNameType   (a: QualifiedName, id: Int = 20) extends BuiltInType
  case class LocalizedTextType   (a: LocalizedText, id: Int = 21) extends BuiltInType
  case class ExtensionObjectType (a: ExtensionObject, id: Int = 22) extends BuiltInType // FIXME: Wrong type used, fix it later
  case class DataValueType       (a: DataValue,       id: Int = 23) extends BuiltInType // FIXME: Wrong type used, fix it later
  case class VariantType         (a: Variant,       id: Int = 24) extends BuiltInType
  case class DiagnosticInfoType  (a: String,        id: Int = 25) extends BuiltInType // FIXME: Wrong type used, fix it later
  object BuiltInType {
    implicit val jsonFormat: OFormat[BuiltInType] = derived.oformat[BuiltInType]()
  }

  // ******************************************* BuiltInTypes  ****************************************************** //

  case class LocalizedText(
    locale: Option[String] = None,
    text: Option[String] = None
  )
  object LocalizedText {
    implicit val jsonFormat: OFormat[LocalizedText] = derived.oformat[LocalizedText]()
  }

  case class QualifiedName(nameSpaceIndex: Int, name: String)
  object QualifiedName {
    implicit val jsonFormat: OFormat[QualifiedName] = derived.oformat[QualifiedName]()
  }

  sealed trait NodeIdIdentifier {
    val value: Any
    def asString: String = value.toString
  }
  case class NumericTwoByteIdentifier(value: Byte) extends NodeIdIdentifier
  case class NumericFourByteIdentifier(value: Short) extends NodeIdIdentifier
  case class NumericIdentifier(value: Int) extends NodeIdIdentifier
  case class StringIdentifier(value: String) extends NodeIdIdentifier
  case class OpaqueIdentifier(value: Vector[Byte]) extends NodeIdIdentifier
  case class GuidIdentifier(value: UUID) extends NodeIdIdentifier
  case class UnknownIdentifier(value: String) extends NodeIdIdentifier
  object NodeIdIdentifier {
    implicit val jsonFormat: OFormat[NodeIdIdentifier] = derived.oformat[NodeIdIdentifier]()
  }

  case class NodeId(
    namespaceIndex: Short,
    identifier: NodeIdIdentifier,
  )
  object NodeId {
    implicit val jsonFormat: OFormat[NodeId] = derived.oformat[NodeId]()
  }
}

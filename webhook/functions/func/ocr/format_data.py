

def create_json_from_response(document):
    json_data = {}
    for entity in document.entities:
        print("---------")
        print(entity)
        print("---------")
        # break
        entity_type = entity.type_
        mention_text = entity.mention_text
        if entity.normalized_value:
            normalized_value = entity.normalized_value.text
        else:
            normalized_value = None
        # print(normalized_value)
        
        if entity_type not in json_data:
            json_data[entity_type] = {}
        
        if mention_text and normalized_value:
            json_data[entity_type][mention_text] = normalized_value
        elif mention_text:
            json_data[entity_type][mention_text] = None
        elif normalized_value:
            json_data[entity_type]["value"] = normalized_value
    
    return json_data
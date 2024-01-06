import { createClient } from 'https://esm.sh/@supabase/supabase-js@2'

// console.log(Deno.env.get('SUPABASE_URL'))
// console.log(Deno.env.get('SUPABASE_ANON_KEY'))

// supabaseClientを作成
export const supabaseClient = createClient(
    Deno.env.get('SUPABASE_URL') ?? '',
    Deno.env.get('SUPABASE_ANON_KEY') ?? ''
)